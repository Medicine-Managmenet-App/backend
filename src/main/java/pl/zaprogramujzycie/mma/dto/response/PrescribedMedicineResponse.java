package pl.zaprogramujzycie.mma.dto.response;

import java.time.OffsetDateTime;
import java.util.List;

public record PrescribedMedicineResponse(

        long id,
        MedicineResponse medicine,
        double dosage,
        List<OffsetDateTime> administrationTimes

) {
}
