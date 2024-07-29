package hello.chatex.chatmanagement.service;

import hello.chatex.chatmanagement.chatDto.ChatRoom;
import hello.chatex.usermanagement.domain.User;
import org.springframework.data.redis.listener.ChannelTopic;

import java.util.List;

/**
 * <br>package name   : hello.chatex.service
 * <br>file name      : ChatRoomService
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
public interface ChatRoomService {
    ChatRoom createChatRoom(String name);
    void enterChatRoom(String roomId);
    List<ChatRoom> getChatRooms();
    ChannelTopic getTopic(String roomId);
}
