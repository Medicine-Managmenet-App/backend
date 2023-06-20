package pl.zaprogramujzycie.mma.dto.response;

import java.time.LocalTime;
import java.util.List;

public record PrescribedMedicineResponse(

        long id,
        long medicineId,
        double dosage,
        List<LocalTime> administrationTimes,
        String owner
) {
}
