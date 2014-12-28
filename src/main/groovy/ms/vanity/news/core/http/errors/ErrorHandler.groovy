package ms.vanity.news.core.http.errors

import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

import javax.servlet.http.HttpServletRequest

@Slf4j
@ControllerAdvice
class ErrorHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleResourceNotFoundException() {
        return new ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(HttpServletRequest req, Exception exception) {
        log.error("Request: " + req.getRequestURL() + " raised exception.", exception);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
