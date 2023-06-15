package pl.zaprogramujzycie.mma.dto.response;

public record UserResponse(

        long id,
        String login,
        String password,
        Long familyId

) {
}
