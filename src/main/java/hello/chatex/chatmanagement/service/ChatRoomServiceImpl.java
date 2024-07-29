package hello.chatex.chatmanagement.service;

import hello.chatex.chatmanagement.chatDto.ChatRoom;
import hello.chatex.chatmanagement.dao.ChatRoomRepository;
import hello.chatex.pubsub.RedisSubscriber;
import hello.chatex.usermanagement.dao.UserRepository;
import hello.chatex.usermanagement.domain.User;
import hello.chatex.usermanagement.domain.UserDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.*;

import static hello.chatex.constants.Const.CHAT_ROOMS;

/**
 * <br>package name   : hello.chatex.service
 * <br>file name      : ChatRoomServiceImpl
 * <br>date           : 2024-07-01
 * <pre>
 * <span style="color: white;">[description]</span>
 *
 * </pre>
 * <pre>
 * <span style="color: white;">usage:</span>
 * {@code
 *
 * } </pre>
 * <pre>
 * modified log :
 * =======================================================
 * DATE           AUTHOR               NOTE
 * -------------------------------------------------------
 * 2024-07-01        SeungHoon              init create
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final UserRepository userRepository;
    // 채팅방의 대화 메세지를 발행하기 위한 redis topic 정보.
    // 서버별로 채팅방에 매치되는 topic 정보를 Map에 넣어서 roomId로 찾을 수 있게 한다.
    private Map<String, ChannelTopic> topics;

    // 채팅방(topic)에 발행되는 메세지를 처리할 Listener
    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;

    private final ChatRoomRepository chatRoomRepository;

    @PostConstruct
    public void init() {
        topics = new HashMap<>();
    }

    @Override
    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .roomId(UUID.randomUUID().toString())
                .build();
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    /**
     * 채팅방 입장 : redis에 topic을 만들고 pub/sub 통신을 하기 위해 리스너를 설정한다.
     */
    @Override
    public void enterChatRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);
        if (topic == null) {
            topic = new ChannelTopic(roomId);
            log.info("topic: {}", topic);
            redisMessageListener.addMessageListener(redisSubscriber,topic);
            topics.put(roomId, topic);
        }
    }

    @Override
    public List<ChatRoom> getChatRooms() {
        return chatRoomRepository.findAll();
    }

    @Override
    public Optional<ChatRoom> getChatRoom(String roomId) {
        return Optional.ofNullable(chatRoomRepository.findById(roomId).orElseThrow(
                () -> new RuntimeException("no chat room found with id: " + roomId)));
    }

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }

    @Override
    public Set<UserDto> getUsers(String roomId) {
        return userRepository.findUsersByRoomId(roomId);
    }
}
