package hello.chatex.chatDto;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

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
public class ChatRoom implements Serializable {

    @Serial
    private static final long serialVersionUID = 768234975743593L;

    private String roomId;
    private String name;

    public static ChatRoom create(String name) {
        ChatRoom room = new ChatRoom();
        room.roomId = UUID.randomUUID().toString();
        room.name = name;
        return room;
    }

}
