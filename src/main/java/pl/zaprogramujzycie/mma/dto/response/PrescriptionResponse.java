package pl.zaprogramujzycie.mma.dto.response;

import java.util.List;

public record PrescriptionResponse(

        long id,
        List<Long> prescribedMedicines

) {
}
