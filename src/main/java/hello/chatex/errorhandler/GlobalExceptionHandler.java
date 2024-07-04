package hello.chatex.errorhandler;

import hello.chatex.errorhandler.exception.RoomNotFoundException;
import hello.chatex.errorhandler.exception.TopicNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <br>package name   : hello.chatex.errorhandler
 * <br>file name      : GloberExceptionHandler
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
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(TopicNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleTopicNotFoundException(TopicNotFoundException e) {
        log.error("Topic not found: {}", e.getMessage());
    }

    @ExceptionHandler(RoomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleRoomNotFoundException(RoomNotFoundException e) {
        log.error("Room not found: {}", e.getMessage());
    }
}
