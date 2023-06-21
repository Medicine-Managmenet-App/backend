package pl.zaprogramujzycie.mma.utils.exceptions;

import lombok.Getter;

import java.util.Date;

public class ErrorMessage {
    @Getter
    private final int statusCode;
    @Getter
    private final Date timestamp;
    @Getter
    private final String message;
    @Getter
    private final String description;

    public ErrorMessage(int statusCode, Date timestamp, String message, String description) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
    }

}