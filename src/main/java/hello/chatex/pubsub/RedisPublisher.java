package hello.chatex.pubsub;

import hello.chatex.chatmanagement.chatDto.ChatMessage;
import hello.chatex.errorhandler.exception.TopicNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatMessage message) {
        try {
            if (topic == null) {
                throw new TopicNotFoundException("Topic cannot be null");
            }
            redisTemplate.convertAndSend(topic.getTopic(), message);
        } catch (TopicNotFoundException e) {
            log.error("Failed to publish message: {}", e.getMessage());
        }
    }
}
