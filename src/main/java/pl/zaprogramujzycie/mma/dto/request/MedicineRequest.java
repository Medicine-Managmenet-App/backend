package pl.zaprogramujzycie.mma.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public record MedicineRequest(

        @NotBlank
        String name,
        @NotNull
        OffsetDateTime expirationDate,
        int periodAfterOpening,
        long owner

) {
}
