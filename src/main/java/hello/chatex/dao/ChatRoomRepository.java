package hello.chatex.dao;

import hello.chatex.chatDto.ChatRoom;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static hello.chatex.constants.Const.CHAT_ROOMS;

/**
 * <br>package name   : hello.chatex.dao
 * <br>file name      : ChatRoomRepository
 * <br>date           : 2024-06-26
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
 * 2024-06-26        SeungHoon              init create
 * </pre>
 */
@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {
    private final RedisTemplate<String,Object> redisTemplate;
    private HashOperations<String,String,ChatRoom> opsHashChatRoom;

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
    }

    public List<ChatRoom> findAllRoom() {
        return opsHashChatRoom.values(CHAT_ROOMS);
    }

    public ChatRoom findRoomById(String roomId) {
        return opsHashChatRoom.get(CHAT_ROOMS, roomId);
    }

    /**
     * 채팅방 생성 : 서버간 채팅의 공유를 위해 redis hash에 저장한다.
     */
    public ChatRoom createChatRoom(String name) {
        ChatRoom room = ChatRoom.builder()
                .name(name)
                .roomId(UUID.randomUUID().toString())
                .messages(new ArrayList<>())
                .build();
        //redis에 저장하기.
        opsHashChatRoom.put(CHAT_ROOMS, room.getRoomId() , room);
        return room;
    }
}
