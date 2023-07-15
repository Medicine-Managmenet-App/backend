package pl.zaprogramujzycie.mma.dto.request;

import javax.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank
        String login,
        @NotBlank
        String password

) {
}
