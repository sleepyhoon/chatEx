package hello.chatex.usermanagement.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * <br>package name   : hello.chatex.usermanagement.domain
 * <br>file name      : UserDto
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

public record UserDto (String name, String roomId){
}
