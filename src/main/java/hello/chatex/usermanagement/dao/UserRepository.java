package hello.chatex.usermanagement.dao;

import hello.chatex.usermanagement.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <br>package name   : hello.chatex.usermanagement.dao
 * <br>file name      : UserRepository
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
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNameAndRoomId(String name, String roomId);
    List<User> findUsersByRoomId(String roomId);
}
