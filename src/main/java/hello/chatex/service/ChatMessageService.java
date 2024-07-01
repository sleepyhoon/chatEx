package hello.chatex.service;

import hello.chatex.chatDto.ChatMessage;

import java.util.List;

/**
 * <br>package name   : hello.chatex.service
 * <br>file name      : ChatMessageService
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
public interface ChatMessageService {
    void saveChatMessage(ChatMessage chatMessage);;
    List<ChatMessage> getChatMessages(String roomId);
}
