package hello.chatex.chatmanagement.dao;

import hello.chatex.chatmanagement.chatDto.ChatMessage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <br>package name   : hello.chatex.dao
 * <br>file name      : ChatMessageRepository
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
@Repository
@RequiredArgsConstructor
public class ChatMessageRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private ListOperations<String,Object> chatMessageList;

    @PostConstruct
    public void init() {
        chatMessageList = redisTemplate.opsForList();
    }

    /**
     * 채팅 로그를 redis에 3일 동안 저장한다. 기간은 수정 가능하다.
     * @param chatMessage
     */
    public void saveMessage(ChatMessage chatMessage) {
        // roomId를 기반으로 메시지를 고유하게 저장
        String key = "CHAT_ROOM_" + chatMessage.getRoomId();
        chatMessageList.rightPush(key,chatMessage);
        redisTemplate.expire(key,3, TimeUnit.DAYS);
    }

    /**
     *
     * @param roomId
     * @return 해당 채팅방 id인 채팅 메시지를 가져온다.
     */
    public List<ChatMessage> getMessagesFromChatRoom(String roomId) {
        // roomId를 기반으로 해당 채팅방의 모든 메시지를 조회
        String key = "CHAT_ROOM_" + roomId;
        List<Object> messages = chatMessageList.range(key, 0, -1);
        if (messages == null) {
            return new ArrayList<>();
        }
        // Object 리스트를 ChatMessage 리스트로 변환
        List<ChatMessage> chatMessages = messages.stream()
                .map(message -> (ChatMessage) message)
                .collect(Collectors.toList());
        // timestamp 순으로 정렬
        chatMessages.sort(Comparator.comparingLong(ChatMessage::getTimestamp));
        return chatMessages;
    }
}
