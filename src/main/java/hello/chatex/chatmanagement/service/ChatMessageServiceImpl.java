package hello.chatex.chatmanagement.service;

import hello.chatex.chatmanagement.chatDto.ChatMessage;
import hello.chatex.chatmanagement.dao.ChatMessageRepository;
import hello.chatex.usermanagement.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <br>package name   : hello.chatex.service
 * <br>file name      : ChatMessageServiceImpl
 * <br>date           : 2024-07-01
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
 * 2024-07-01        SeungHoon              init create
 * </pre>
 */
@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    @Override
    @Transactional
    @CacheEvict(value = "chatMessages", key = "#chatMessage.roomId")
    public void saveChatMessage(ChatMessage chatMessage) {
        chatMessageRepository.saveMessage(chatMessage);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "chatMessages", key = "#roomId")
    public List<ChatMessage> getChatMessages(String roomId) {
        return chatMessageRepository.getMessagesFromChatRoom(roomId);
    }
}
