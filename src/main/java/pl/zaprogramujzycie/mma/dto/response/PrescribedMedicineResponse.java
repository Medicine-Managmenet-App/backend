package pl.zaprogramujzycie.mma.dto.response;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;

public record PrescribedMedicineResponse(

        long id,
        long medicine,
        double dosage,
        int numberOfDoses,
        int dosageInterval,
        @NotNull
        List<LocalTime> administrationTimes

) {
}
