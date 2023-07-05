package pl.zaprogramujzycie.mma.exceptions;


import java.util.Date;

public record ErrorMessage (
        int statusCode,
        Date timestamp,
        String description
) {
}