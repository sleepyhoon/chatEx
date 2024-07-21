package hello.chatex.chatmanagement.chatController;

import hello.chatex.chatmanagement.chatDto.ChatMessage;
import hello.chatex.chatmanagement.chatlog.ChatLogManager;
import hello.chatex.pubsub.RedisPublisher;
import hello.chatex.chatmanagement.service.ChatMessageService;
import hello.chatex.chatmanagement.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * <br>package name   : hello.chatex.chatController
 * <br>file name      : ChatMessageController
 * <br>date           : 2024-06-26
 * <pre>
 * <span style="color: white;">[description]</span>
 * ChatMessage에 관련된 url을 처리하는 controller 이다. websocket "/pub/chat/message"로 들어오는 메세지를 처리하거나, 특정 채팅방에 있는 모든 유저들을 반환한다.
 * </pre>
 * <pre>
 * <span style="color: white;">usage:</span>
 * {@code
 * public void message(ChatMessage chatMessage) throws IOException
 * public List<ChatMessage> getMessages(@PathVariable String roomId)
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
@Slf4j
public class ChatMessageController {
    private final RedisPublisher redisPublisher;
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final ChatLogManager chatLogManager;

    /**
     * websocket "/pub/chat/message"로 들어오는 메세지를 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(@Payload ChatMessage chatMessage) throws IOException {
        // 처음 입장 했을 경우(Enter)
        if(ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
            chatRoomService.enterChatRoom(chatMessage.getRoomId());
            chatMessage.setMessage(chatMessage.getSender()+"님이 입장하셨습니다.");
        }
        // 채팅을 입력하는 경우 메세지를 minIO에 저장해야함. redis에는 cache miss 발생하면 저장해야함.
        if(ChatMessage.MessageType.TALK.equals(chatMessage.getType())) {
            chatMessageService.saveInMinio(chatMessage);
            chatLogManager.saveChatMessage(chatMessage);
        }
        // 기존 유저가 입장하는 경우(Join), 아무것도 출력하지않음.
        // Websocket에 발행된 메세지를 redis로 발행한다.
        ChannelTopic topic = chatRoomService.getTopic(chatMessage.getRoomId());
        redisPublisher.publish(topic, chatMessage);
    }
    /**
     * 특정 채팅방의 모든 메시지를 조회한다.
     */
    @GetMapping("/chat/messages/{roomId}")
    @ResponseBody
    public List<ChatMessage> getMessages(@PathVariable String roomId) {
        return chatMessageService.getChatMessages(roomId);
    }
}
