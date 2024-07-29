package hello.chatex.chatmanagement.chatDto;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * <br>package name   : hello.chatex.chatDto
 * <br>file name      : chatMessage
 * <br>date           : 2024-06-26
 * <pre>
 * <span style="color: white;">[description]</span>
 * 채팅에는 2가지 종류가 있다.
 * - Enter : 채팅방에 처음 입장함. 그 때 안내문을 작성하기 위해 사용한다.
 * - Talk : 채팅방에서 유저들이 작성한 채팅을 나타낸다.
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
@Document(collection = "chat")
public class ChatMessage  {
    // 메세지 타입 : 새로 운 유저 입장, 기존의 유저 입장, 채팅
    public enum MessageType {
        ENTER, JOIN, TALK
    }

    @Id
    private String id;

    private MessageType type; // 메세지 타입

    @Setter
    private String message; // 메세지 내용

    private String roomId; // 채팅방 id

    private String sender; // 송신자

    private long timestamp; // 메세지 작성 시간
}
