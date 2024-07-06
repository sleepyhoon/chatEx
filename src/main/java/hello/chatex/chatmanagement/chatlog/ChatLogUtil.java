package hello.chatex.chatmanagement.chatlog;

import hello.chatex.chatmanagement.chatDto.ChatMessage;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * <br>package name   : hello.chatex.chatmanagement.chatlog
 * <br>file name      : ChatLogUtil
 * <br>date           : 2024-07-06
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
 * 2024-07-06        SeungHoon              init create
 * </pre>
 */
@Component
public class ChatLogUtil {
    /**
     * ChatMessage를 log 형식에 맞게 재구성한다.
     *
     * @param message
     * @return [DATE and Time] User (Chat_Type): ChatMessage (Room ID: ~~)
     */
    public static String formatChatMessage(ChatMessage message) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date(message.getTimestamp()));
        return String.format("[%s] %s (%s): %s (Room ID: %s)",
                timestamp, message.getSender(), message.getType(), message.getMessage(), message.getRoomId());
    }

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
}
