package pl.zaprogramujzycie.mma.dto.response;

import java.util.List;

public record FamilyResponse(
        long id,
        List<Long> members,
        List<String> users,
        List<Long> medicines
) {
}
