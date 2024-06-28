package hello.chatex.pubsub;

import hello.chatex.chatDto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

/**
 * <br>package name   : hello.chatex.pubsub
 * <br>file name      : RedisPublisher
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
@RequiredArgsConstructor
@Service
public class RedisPublisher {
    private final RedisTemplate<String, String> redisTemplate;

    public void publish(ChannelTopic topic, ChatMessage message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
