package hello.chatex.minio;

import hello.chatex.chatmanagement.chatDto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <br>package name   : hello.chatex.minio
 * <br>file name      : MinioServiceImpl
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
@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioRepository minioRepository;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Override
    public void save(ChatMessage chatMessage) {
        MinioSaveChatDto dto = MinioSaveChatDto.builder()
                .bucketName(bucketName)
                .fileName("chat/"+chatMessage.getRoomId()+"/"+"chatting")
                .fileExtension("json")
                .build();
        minioRepository.uploadChatting(chatMessage,dto);
    }
}
