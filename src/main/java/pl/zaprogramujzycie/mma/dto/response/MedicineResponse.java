package pl.zaprogramujzycie.mma.dto.response;

import java.time.OffsetDateTime;

public record MedicineResponse(

        long id,
        String name,
        OffsetDateTime expirationDate,
        int periodAfterOpening,
        String owner
) {
}
