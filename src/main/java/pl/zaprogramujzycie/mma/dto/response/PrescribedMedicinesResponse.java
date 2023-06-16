package pl.zaprogramujzycie.mma.dto.response;

import java.util.List;

public record PrescribedMedicinesResponse(

        List<PrescribedMedicineResponse> prescribedMedicines

) {
}
