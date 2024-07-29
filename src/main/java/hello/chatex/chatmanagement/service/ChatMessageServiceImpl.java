package hello.chatex.chatmanagement.service;

import hello.chatex.chatmanagement.chatDto.ChatMessage;
import hello.chatex.chatmanagement.dao.ChatMessageRepository;
import hello.chatex.chatmanagement.dao.ChatRoomRepository;
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

    private final ChatMessageRepository chatMessageRepository;

    @Override
    public void saveMessage(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessage> getChatMessages(String roomId) {
        return chatMessageRepository.findByRoomIdOrderByTimestampDesc(roomId);
    }
}
