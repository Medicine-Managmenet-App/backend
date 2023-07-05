package pl.zaprogramujzycie.mma.exceptions.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.zaprogramujzycie.mma.exceptions.LoginException;

@RestControllerAdvice
@Slf4j
public class SecurityAdvice {

    @ExceptionHandler(LoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleLoginException(final LoginException loginException) {
        log.info("handleLoginException() - error = {}", loginException.toString());
    }
}
