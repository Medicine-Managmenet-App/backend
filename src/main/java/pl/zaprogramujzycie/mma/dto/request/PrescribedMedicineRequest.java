package pl.zaprogramujzycie.mma.dto.request;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

public record PrescribedMedicineRequest(

        @NotNull
        long medicineId,
        @NotNull
        double dosage,
        @NotNull
        LocalTime firstDose,
        int dosageInterval,
        String owner

) {
}
