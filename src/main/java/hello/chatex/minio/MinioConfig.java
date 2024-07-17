package hello.chatex.minio;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <br>package name   : hello.chatex.minio
 * <br>file name      : MinioConfig
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
@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("http://jack8226.ddns.net:3100")
                .credentials("jack8226", "m7128226")
                .build();
    }
}
