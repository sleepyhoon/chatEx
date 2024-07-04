package hello.chatex.errorhandler.exception;

/**
 * <br>package name   : hello.chatex.errorhandler
 * <br>file name      : TopicNotFoundException
 * <br>date           : 2024-07-04
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
 * 2024-07-04        SeungHoon              init create
 * </pre>
 */
public class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException(String message) {
        super(message);
    }
}
