package hello.chatex.minio;

import hello.chatex.chatmanagement.chatDto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <br>package name   : hello.chatex.minio
 * <br>file name      : MinioService
 * <br>date           : 2024-07-17
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
 * 2024-07-17        SeungHoon              init create
 * </pre>
 */
public interface MinioService {
    void save(ChatMessage chatMessage);
}
