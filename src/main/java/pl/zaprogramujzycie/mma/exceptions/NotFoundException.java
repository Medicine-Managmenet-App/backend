package pl.zaprogramujzycie.mma.exceptions;

import org.springframework.data.crossstore.ChangeSetPersister;

public class NotFoundException extends ChangeSetPersister.NotFoundException {
    public NotFoundException() {
        super();
    }
}
