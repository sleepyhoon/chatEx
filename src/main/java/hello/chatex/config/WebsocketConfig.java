package hello.chatex.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * <br>package name   : hello.chatex
 * <br>file name      : WebsocketConfig
 * <br>date           : 2024-06-26
 * <pre>
 * <span style="color: white;">[description]</span>
 * Websocket 과 stomp을 동시에 사용하기 위한 Configuration.
 * </pre>
 * <pre>
 * <span style="color: white;">usage:</span>
 * {@code
 * @MessageMapping("/chat/message") : websocket "/pub/chat/message"로 들어오는 메세지를 처리한다.
 * messagingTemplate.convertAndSend("/sub/chat/room/"+roomMessage.getRoomId(),roomMessage) : Websocket 구독자들에게 채팅 메세지 전송.
 * } </pre>
 * <pre>
 * modified log :
 * =======================================================
 * DATE           AUTHOR               NOTE
 * -------------------------------------------------------
 * 2024-06-26        SeungHoon              init create
 * </pre>
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * "/sub"은 message 구독할 때 사용한다. => 메세지를 구독하는 요청 url <br>
     * "/pub"은 message 발행할 때 사용한다. => 메세지를 발행하는 요청 url
     * @param config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/pub");
    }

    /**
     * Endpoint를 <b>"/ws-stomp"</b>로 설정하여 웹소켓 통신이 <b>"/ws-stomp"</b> 으로 도착하는 통신은
     * stomp 통신임을 확인하고, 이를 연결한다는 의미이다.
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")
                .setAllowedOriginPatterns("*");
    }
}
