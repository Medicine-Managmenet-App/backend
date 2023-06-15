package pl.zaprogramujzycie.mma.dto.response;

import java.util.List;

public record FamilyResponse(
        long id,
        List<FamilyMemberResponse> members,
        List<UserResponse> users
) {
}
