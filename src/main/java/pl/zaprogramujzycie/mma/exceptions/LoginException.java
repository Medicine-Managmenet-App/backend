package pl.zaprogramujzycie.mma.exceptions;

import javax.validation.constraints.NotBlank;

public class LoginException extends RuntimeException {
    public LoginException(@NotBlank String username, @NotBlank String password) {
        super("Failed to authenticate with username = %s, password = %s".formatted(username, password));
    }
}
