package hello.chatex.chatController;

import hello.chatex.chatDto.ChatMessage;
import hello.chatex.chatDto.ChatRoom;
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
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if(ChatMessage.MessageType.JOIN.equals(message.getType())){
            message.setMessage(message.getSender()+"님이 입장하셨습니다.");
        }
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

}
