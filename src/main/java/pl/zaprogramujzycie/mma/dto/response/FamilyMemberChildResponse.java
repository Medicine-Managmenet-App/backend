package pl.zaprogramujzycie.mma.dto.response;

public record FamilyMemberChildResponse(

        long id,
        String name,
        boolean isChild,
        PrescriptionResponse prescription,
        Long familyId,
        int age,
        float weight

) {
}
