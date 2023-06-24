package pl.zaprogramujzycie.mma.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record FamilyMemberRequest(

        @NotBlank
        String name,
        @NotNull
        boolean isChild,
        long prescriptionId,
        Long familyId,
        int age,
        float weight

) {
}
