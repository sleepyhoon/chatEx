package hello.chatex.usermanagement.service;

import hello.chatex.chatmanagement.chatDto.ChatRoom;
import hello.chatex.chatmanagement.dao.ChatRoomRepository;
import hello.chatex.usermanagement.dao.UserRepository;
import hello.chatex.usermanagement.domain.User;
import hello.chatex.usermanagement.domain.UserDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * <br>package name   : hello.chatex.usermanagement.service
 * <br>file name      : UserServiceImpl
 * <br>date           : 2024-07-03
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
 * 2024-07-03        SeungHoon              init create
 * </pre>
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    /**
     *
     * @param userDto
     * @return 같은 채팅방 내에서는 중복 이름이 불가능하지만, 다른 채팅방 내에서는 중복 이름이 가능함.
     */
    @Override
    @Transactional
    public User createUser(UserDto userDto) {
        try {
            return userRepository.findByNameAndRoomId(userDto.getName(), userDto.getRoomId())
                    .orElseGet(() -> {
                        User user = User.builder()
                                .name(userDto.getName())
                                .roomId(userDto.getRoomId())
                                .build();
                        return userRepository.save(user);
                    });
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("중복된 사용자: " + userDto.getName() + " in room " + userDto.getRoomId(), e);
        }
    }

    @Override
    public void addUser(String roomId, UserDto userDto) {
    }

    @Override
    public Set<UserDto> getUsers(String roomId) {
        return Set.of();
    }
}
