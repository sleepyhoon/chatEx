package hello.chatex.chatDto;

import lombok.Getter;
import lombok.Setter;

/**
 * <br>package name   : hello.chatex.chatDto
 * <br>file name      : chatMessage
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
@Setter
public class ChatMessage {
    // 메세지 타입 : 입장, 채팅
    public enum MessageType {
        ENTER, JOIN, TALK
    }
    private MessageType type; // 메세지 타입

    private String message; // 메세지 내용

    private String roomId; // 채팅방 id

    private String sender; // 송신자
}
