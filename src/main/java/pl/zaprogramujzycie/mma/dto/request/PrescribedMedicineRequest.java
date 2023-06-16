package pl.zaprogramujzycie.mma.dto.request;

import pl.zaprogramujzycie.mma.dto.response.MedicineResponse;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

public record PrescribedMedicineRequest(

        @NotNull
        long medicine,
        @NotNull
        double dosage,
        @NotNull
        List<OffsetDateTime> administrationTimes

) {
}