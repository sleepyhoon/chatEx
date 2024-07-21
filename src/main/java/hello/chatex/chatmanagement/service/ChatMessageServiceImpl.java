package hello.chatex.chatmanagement.service;

import hello.chatex.chatmanagement.chatDto.ChatMessage;
import hello.chatex.minio.MinioRepository;
import hello.chatex.minio.MinioSaveChatDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <br>package name   : hello.chatex.service
 * <br>file name      : ChatMessageServiceImpl
 * <br>date           : 2024-07-01
 * <pre>
 * <span style="color: white;">[description]</span>
 * 채팅 메세지를 저장하거나, 조회할 수 있다. 캐시 전략으로 Look aside, Write Around 전략을 사용한다.
 * </pre>
 * <pre>
 * <span style="color: white;">usage:</span>
 * {@code
 * public void saveChatMessage(ChatMessage chatMessage)
 * public List<ChatMessage> getChatMessages(String roomId)
 * } </pre>
 * <pre>
 * modified log :
 * =======================================================
 * DATE           AUTHOR               NOTE
 * -------------------------------------------------------
 * 2024-07-01        SeungHoon              init create
 * </pre>
 */
@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final MinioRepository minioRepository;

    private final RedisTemplate<String, Object> redisTemplate;
    private ListOperations<String,Object> chatMessageList;

    @PostConstruct
    public void init() {
        chatMessageList = redisTemplate.opsForList();
    }

    @Value("${minio.bucketName}")
    private String bucketName;

    @Override
    public void saveMessageInMinio(ChatMessage chatMessage) {
        String uniqueName = "chatting_" + chatMessage.getTimestamp();
        MinioSaveChatDto dto = MinioSaveChatDto.builder()
                .bucketName(bucketName)
                .fileName("chat/"+"/Chatting/ChatRoom_"+chatMessage.getRoomId()+"/"+uniqueName)
                .fileExtension("json")
                .build();
        minioRepository.uploadFile(chatMessage,dto);
    }

    @Override
    @Transactional
    public void saveInRedis(ChatMessage chatMessage) {
        // roomId를 기반으로 메시지를 고유하게 저장
        String key = "CHAT_ROOM_" + chatMessage.getRoomId();
        chatMessageList.rightPush(key,chatMessage);
    }

    /**
     * 캐시와 DB를 동기화해서 가져오는 것이 필요함. 그래서 동기화하는 과정을 추가했음.
     * @param roomId
     * @return
     */

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessage> getChatMessages(String roomId) {
        // redis에서 roomId를 기반으로 해당 채팅방의 모든 메시지를 조회
        String key = "CHAT_ROOM_" + roomId;
        List<Object> messages = chatMessageList.range(key, 0, -1);

        List<ChatMessage> chatMessages;
        if (messages == null || messages.isEmpty()) {
            // redis에 없다면 minio DB에서 가져와야 한다. 그리고 redis에 저장한다.
            chatMessages = minioRepository.getChatMessages(bucketName, roomId);
            for(ChatMessage chatMessage : chatMessages) {
                // roomId를 기반으로 메시지를 고유하게 저장
                key = "CHAT_ROOM_" + chatMessage.getRoomId();
                chatMessageList.rightPush(key,chatMessage);
            }
            // timestamp 순으로 정렬
            chatMessages.sort(Comparator.comparingLong(ChatMessage::getTimestamp));
        }
        else {
            // Object 리스트를 ChatMessage 리스트로 변환
            chatMessages = messages.stream()
                    .map(message -> (ChatMessage) message)
                    .collect(Collectors.toList());

            // Minio에서 최신 데이터를 가져옴
            List<ChatMessage> dbMessages = minioRepository.getChatMessages(bucketName, roomId);

            // 최신 데이터를 비교하여 동기화
            if (!dbMessages.equals(chatMessages)) {
                // Minio 데이터를 기준으로 Redis를 동기화
                redisTemplate.delete(key); // 기존 데이터를 삭제
                for (ChatMessage dbMessage : dbMessages) {
                    chatMessageList.rightPush(key, dbMessage);
                }
                chatMessages = dbMessages;
            }
        }
        return chatMessages;
    }
}
