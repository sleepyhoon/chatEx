package hello.chatex.minio;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.chatex.chatmanagement.chatDto.ChatMessage;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * <br>package name   : hello.chatex.minio
 * <br>file name      : MinioRepository
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
@Repository
@RequiredArgsConstructor
public class MinioRepository {

    private final MinioClient minioClient;
    private final ObjectMapper objectMapper;

    public void uploadChatting(ChatMessage chatMessage, MinioSaveChatDto dto) {
        try {
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(dto.getBucketName()).build());
            if(!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(dto.getBucketName()).build());
                minioClient.setBucketPolicy(
                        SetBucketPolicyArgs.builder()
                                .bucket(dto.getBucketName())
                                .config("public")
                                .build()
                );
            }

            // 채팅 메시지를 JSON 형식으로 변환
            String jsonMessage = objectMapper.writeValueAsString(chatMessage);
            byte[] jsonBytes = jsonMessage.getBytes(StandardCharsets.UTF_8);

            try (InputStream inputStream = new ByteArrayInputStream(jsonBytes)) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(dto.getBucketName())
                                .object(dto.getFileName() + "." + dto.getFileExtension())
                                .stream(inputStream, jsonBytes.length, -1)
                                .contentType("application/json")
                                .build()
                );
            }
        } catch(Exception e) {
            throw new RuntimeException("Error occur : " + e.getMessage());
        }
    }

    public List<ChatMessage> getChatMessages(String bucketName, String roomId) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        try {
            // 객체 목록을 조회하여 roomId로 시작하는 객체들을 찾습니다.
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .prefix(roomId + "/")
                            .build());

            for (Result<Item> result : results) {
                Item item = result.get();
                try (InputStream stream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(bucketName)
                                .object(item.objectName())
                                .build())) {
                    ChatMessage chatMessage = objectMapper.readValue(stream, ChatMessage.class);
                    chatMessages.add(chatMessage);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching chat messages: " + e.getMessage());
        }
        return chatMessages;
    }
}
