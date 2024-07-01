package hello.chatex.chatController;

import hello.chatex.chatDto.ChatMessage;
import hello.chatex.chatDto.ChatRoom;
import hello.chatex.dao.ChatRoomRepository;
import hello.chatex.pubsub.RedisPublisher;
import hello.chatex.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <br>package name   : hello.chatex.chatController
 * <br>file name      : chatController
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
@Controller
public class ChatController {
    private final RedisPublisher redisPublisher;
    private final ChatRoomService chatRoomService;

    /**
     * websocket "/pub/chat/message"로 들어오는 메세지를 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage chatMessage) {
        if(ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
            chatRoomService.enterChatRoom(chatMessage.getRoomId());
            chatMessage.setMessage(chatMessage.getSender()+"님이 입장하셨습니다.");
        }
        // Websocket에 발행된 메세지를 redis로 발행한다.
        redisPublisher.publish(chatRoomService.getTopic(chatMessage.getRoomId()),chatMessage);
    }

}
