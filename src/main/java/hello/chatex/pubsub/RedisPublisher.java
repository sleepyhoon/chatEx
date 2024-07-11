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
 * 해당 토픽을 가진 채팅방에 메세지를 발행한다. 해당 코드가 실행되면 자동으로 RedisSubscriber의 onMessage() 메서드가 실행된다. 전달 받은 topic이 null 이면 예외를 발생시킨다.
 * </pre>
 * <pre>
 * <span style="color: white;">usage:</span>
 * {@code
 * public void publish(ChannelTopic topic, ChatMessage message)
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
