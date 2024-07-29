package hello.chatex.usermanagement.service;

import hello.chatex.usermanagement.domain.User;
import hello.chatex.usermanagement.domain.UserDto;

import java.util.List;
import java.util.Set;

/**
 * <br>package name   : hello.chatex.usermanagement.service
 * <br>file name      : UserService
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
public interface UserService {
    User createUser(UserDto userDto);
}
