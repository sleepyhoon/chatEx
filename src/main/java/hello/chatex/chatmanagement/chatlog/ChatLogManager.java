package hello.chatex.chatmanagement.chatlog;

import hello.chatex.chatmanagement.chatDto.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static hello.chatex.constants.Const.LOG_DIRECTORY;

/**
 * <br>package name   : hello.chatex.chatmanagement.chatlog
 * <br>file name      : ChatLogManager
 * <br>date           : 2024-07-06
 * <pre>
 * <span style="color: white;">[description]</span>
 * 채팅 로그를 작성을 관리하는 Manager class
 * </pre>
 * <pre>
 * <span style="color: white;">usage:</span>
 * {@code
 *  saveChatMessage(ChatMessage message) : 채팅 로그를 저장
 *  formatChatMessage(ChatMessage message) : 채팅 메세지를
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
@Slf4j
public class ChatLogManager {

    /**
     * log를 저장하는 디렉토리가 없으면 생성한다.
     */
    public ChatLogManager() {
        File dir = new File(LOG_DIRECTORY);
        if (!dir.exists()) {
            log.info("Create directory: {}", LOG_DIRECTORY);
            dir.mkdirs();
        }
    }

    /**
     * local에 존재하는 chat_logs directory에 log를 저장한다.
     * @param message
     * @throws IOException
     */
    public static synchronized void saveChatMessage(ChatMessage message) throws IOException {
        String fileName = LOG_DIRECTORY + getCurrentDate() + "_chatLog.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(formatChatMessage(message));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ChatMessage를 log 형식에 맞게 재구성한다.
     * @param message
     * @return [DATE and Time] User (Chat_Type): ChatMessage (Room ID: ~~)
     */
    private static String formatChatMessage(ChatMessage message) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date(message.getTimestamp()));
        return String.format("[%s] %s (%s): %s (Room ID: %s)",
                timestamp, message.getSender(), message.getType(), message.getMessage(), message.getRoomId());
    }

    private static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
}
