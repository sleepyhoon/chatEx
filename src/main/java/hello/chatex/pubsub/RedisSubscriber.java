package hello.chatex.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.chatex.chatDto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

/**
 * <br>package name   : hello.chatex.pubsub
 * <br>file name      : RedisSubscriber
 * <br>date           : 2024-06-28
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
 * 2024-06-28        SeungHoon              init create
 * </pre>
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * redis에서 메세지가 발행되면 대기 하고 있던 onMessage()가 데이터를 처리한다.
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // redis에서 발행된 메세지를 받아 역직렬화
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            // ChatMessage 객체로 매핑
            ChatMessage roomMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
            // Websocket 구독자들에게 채팅 메세지 send
            messagingTemplate.convertAndSend("/sub/chat/room/"+roomMessage.getRoomId(),roomMessage);
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage(), e);
        }
    }
}
