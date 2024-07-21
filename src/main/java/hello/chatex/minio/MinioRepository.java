package hello.chatex.minio;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.chatex.chatmanagement.chatDto.ChatMessage;
import hello.chatex.chatmanagement.chatDto.ChatRoom;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

    /**
     * minio에 채팅 메세지를 저장함.
     * @param object
     * @param dto
     */
    public void uploadFile(Object object, MinioSaveChatDto dto) {
        try {
            makeBucket(dto);

            // 채팅 메시지를 JSON 형식으로 변환
            String jsonMessage = objectMapper.writeValueAsString(object);
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

    private void makeBucket(MinioSaveChatDto dto) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
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
    }

    /**
     * minio에서 채팅메세지를 가져옴.
     * @param bucketName
     * @param roomId
     * @return
     */
    public List<ChatMessage> getChatMessages(String bucketName, String roomId) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .prefix("chat/Chatting/ChatRoom_"+ roomId + "/")
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

    /**
     * 채팅을 저장하는 과정에서 생성되는 폴더가 채팅방의 역할을 한다. 그렇다면 굳이 내가 따로 채팅방을 저장해야 하는 이유가 있는가?
     * 채팅방에 기능이 좀 더 필요하면 따로 저장을 해야하는 가치가 있을지도 모르겠다. 당장 해당 채팅방에 위치한 유저들을 출력하려고 해도 필요한듯.
     * @param bucketName
     * @return
     */
    public List<ChatRoom> getChatRooms(String bucketName) {
        List<ChatRoom> chatRooms = new ArrayList<>();
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .prefix("chat/ChatRoom/")
                            .build());

            for (Result<Item> result : results) {
                Item item = result.get();
                try (InputStream stream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(bucketName)
                                .object(item.objectName())
                                .build())) {
                    ChatRoom chatroom = objectMapper.readValue(stream, ChatRoom.class);
                    chatRooms.add(chatroom);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching chat rooms: " + e.getMessage());
        }
        return chatRooms;
    }

    public void deleteChatRoom(String bucketName, String roomId) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object("chat/ChatRoom_/" + roomId + ".json")
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while deleting chat room: " + e.getMessage());
        }
    }
}
