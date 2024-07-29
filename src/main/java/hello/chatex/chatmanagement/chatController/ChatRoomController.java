package hello.chatex.chatmanagement.chatController;

import hello.chatex.chatmanagement.chatDto.ChatRoom;
import hello.chatex.chatmanagement.dao.ChatRoomRepository;
import hello.chatex.chatmanagement.service.ChatRoomService;
import hello.chatex.usermanagement.domain.UserDto;
import hello.chatex.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * <br>package name   : hello.chatex.chatController
 * <br>file name      : ChatRoomController
 * <br>date           : 2024-06-26
 * <pre>
 * <span style="color: white;">[description]</span>
 * 클라이언트가 직접적으로 보내는 요청을 처리한다.
 * </pre>
 * <pre>
 * <span style="color: white;">usage:</span>
 * {@code
 * public String rooms(Model model)
 * public List<ChatRoom> room()
 * public ChatRoom createRoom(@RequestParam String name)
 * public String roomDetail(@PathVariable String roomId, Model model)
 * public ChatRoom roomInfo(@PathVariable String roomId)
 * public List<User> getUsersInRoom(@PathVariable String roomId)
 * } </pre>
 * <pre>
 * modified log :
 * =======================================================
 * DATE           AUTHOR               NOTE
 * -------------------------------------------------------
 * 2024-06-26        SeungHoon              init create
 * </pre>
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomService chatRoomService;

    private final UserService userService;

    // 채팅방 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        return "room";
    }

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatRoomService.getChatRooms();
    }

    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomService.createChatRoom(name);
    }

    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(@PathVariable String roomId, Model model) {
        model.addAttribute("roomId", roomId);
        return "roomdetail";
    }

    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public Optional<ChatRoom> roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }

    // 특정 채팅방에 있는 유저들 조회
    @GetMapping("/room/{roomId}/users")
    @ResponseBody
    public Set<UserDto> getUsersInRoom(@PathVariable String roomId) {
        return chatRoomService.getUsers(roomId);
    }
}
