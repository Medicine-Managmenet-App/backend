package pl.zaprogramujzycie.mma.dto.request;

import java.util.List;

public record PrescriptionRequest(

        List<Long> prescribedMedicines

) {
}
