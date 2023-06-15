package pl.zaprogramujzycie.mma.dto.response;

import java.util.List;

public record FamilyMembersResponse(

        List<FamilyMemberResponse> members

) {
}
