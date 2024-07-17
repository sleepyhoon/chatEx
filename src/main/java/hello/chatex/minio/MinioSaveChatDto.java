package hello.chatex.minio;

import hello.chatex.chatmanagement.chatDto.ChatMessage;
import lombok.Builder;
import lombok.Getter;

/**
 * <br>package name   : hello.chatex.chatmanagement.chatDto
 * <br>file name      : MinioSaveChatDto
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
@Builder
@Getter
public class MinioSaveChatDto {
    private String bucketName;
    private String fileName;
    private String fileExtension;
}
