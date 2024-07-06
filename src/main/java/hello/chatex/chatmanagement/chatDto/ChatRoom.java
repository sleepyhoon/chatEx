package hello.chatex.chatmanagement.chatDto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * <br>package name   : hello.chatex.chatDto
 * <br>file name      : chatRoom
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
@Getter
@Builder
public class ChatRoom implements Serializable {

    @Serial
    private static final long serialVersionUID = 123456789L;

    private String roomId;
    private String name;
}
