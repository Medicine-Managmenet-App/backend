package pl.zaprogramujzycie.mma.dto.request;

import javax.validation.constraints.NotBlank;

public record UserRequest(

        @NotBlank
        String login,
        @NotBlank
        String password

) {
}
