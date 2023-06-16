package pl.zaprogramujzycie.mma.dto.request;

import java.util.List;

public record FamilyRequest(

        List<Long> members,
        List<Long> users

) {
}
