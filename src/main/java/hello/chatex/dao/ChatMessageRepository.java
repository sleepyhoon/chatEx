package hello.chatex.dao;

import hello.chatex.chatDto.ChatMessage;
import hello.chatex.chatDto.ChatRoom;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private HashOperations<String,String, ChatMessage> chatMessageListOps;

    @PostConstruct
    public void init() {
        chatMessageListOps = redisTemplate.opsForHash();
    }

    public void saveMessage(ChatMessage chatMessage) {
        // roomId를 기반으로 메시지를 고유하게 저장
        String key = "CHAT_ROOM_" + chatMessage.getRoomId();
        String messageId = generateMessageId(chatMessage);
        chatMessageListOps.put(key, messageId, chatMessage);
    }

    public List<ChatMessage> findMessagesByRoomId(String roomId) {
        // roomId를 기반으로 해당 채팅방의 모든 메시지를 조회
        String key = "CHAT_ROOM_" + roomId;
        Map<String, ChatMessage> messages = chatMessageListOps.entries(key);
        return new ArrayList<>(messages.values());
    }

    private String generateMessageId(ChatMessage chatMessage) {
        // 메시지 고유 ID 생성 로직 (예: 타임스탬프와 송신자를 조합하여 생성)
        return chatMessage.getSender() + "_" + System.currentTimeMillis();
    }
}
