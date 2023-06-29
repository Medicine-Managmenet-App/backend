package pl.zaprogramujzycie.mma.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import pl.zaprogramujzycie.mma.exceptions.ErrorMessage;

import java.util.Date;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //ToDo work on messages to user
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> NotFoundException(final NotFoundException ex, final WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<ErrorMessage> unauthorizedException (final HttpClientErrorException.Unauthorized ex, final WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                request.getDescription(false));

        return  new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<ErrorMessage> unauthorizedException (final HttpClientErrorException.Forbidden ex, final WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                request.getDescription(false));

        return  new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<ErrorMessage> badRequestException(final HttpClientErrorException.BadRequest ex, final WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                request.getDescription(false));

        return  new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> dataIntegrityViolationException(final DataIntegrityViolationException ex, final WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.IM_USED.value(),
                new Date(),
                request.getDescription(false));

        return  new ResponseEntity<>(message, HttpStatus.IM_USED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}