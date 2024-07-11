package hello.chatex.chatmanagement.chatlog;

import hello.chatex.chatmanagement.chatDto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static hello.chatex.chatmanagement.chatlog.ChatLogUtil.formatChatMessage;
import static hello.chatex.chatmanagement.chatlog.ChatLogUtil.getCurrentDate;
import static hello.chatex.constants.Const.LOG_DIRECTORY;

/**
 * <br>package name   : hello.chatex.chatmanagement.chatlog
 * <br>file name      : ChatLogManager
 * <br>date           : 2024-07-06
 * <pre>
 * <span style="color: white;">[description]</span>
 * 채팅 로그를 작성을 관리하는 Manager class
 * 채팅 로그를 로컬에 지정한 directoru에 만든다. 없으면 만든다.
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
     * local에 존재하는 chat_logs directory에 log를 저장한다.
     * @param message
     * @throws IOException
     */
    public synchronized void saveChatMessage(ChatMessage message) throws IOException {
        ensureLogDirectoryExists();
        String fileName = LOG_DIRECTORY + getCurrentDate() + "_chatLog.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(formatChatMessage(message));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void ensureLogDirectoryExists() {
        File dir = new File(LOG_DIRECTORY);
        if (!dir.exists()) {
            log.info("Create directory: {}", LOG_DIRECTORY);
            dir.mkdirs();
        }
    }
}
