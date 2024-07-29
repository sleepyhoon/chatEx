package hello.chatex.chatmanagement.chatDto;

import hello.chatex.usermanagement.domain.User;
import hello.chatex.usermanagement.domain.UserDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

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
@Document(collection = "chatroom")
public class ChatRoom {

    private String roomId;
    private String name;
}
