package hello.chatex.service;

import org.springframework.data.redis.listener.ChannelTopic;

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
    void enterChatRoom(String roomId);
    ChannelTopic getTopic(String roomId);
}
