package hello.chatex.chatmanagement.chatDto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * <br>package name   : hello.chatex.chatmanagement.chatDto
 * <br>file name      : UserChatRoom
 * <br>date           : 2024-07-25
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
 * 2024-07-25        SeungHoon              init create
 * </pre>
 */
@Getter
@Builder
public class UserChatRoom implements Serializable {

    private Long userId;
    private String chatRoomId;
}
