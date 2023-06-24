package pl.zaprogramujzycie.mma.dto.response;

public record FamilyMemberResponse(

        long id,
        String name,
        boolean isChild,
        long prescriptionId,
        Long familyId

) {
}
