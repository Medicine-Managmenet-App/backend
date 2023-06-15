package pl.zaprogramujzycie.mma.dto.request;

import pl.zaprogramujzycie.mma.dto.response.PrescriptionResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record FamilyMemberRequest(

        @NotBlank
        String name,
        @NotNull
        boolean isChild,
        PrescriptionResponse prescription,
        Long familyId,
        int age,
        float weight

) {
}
