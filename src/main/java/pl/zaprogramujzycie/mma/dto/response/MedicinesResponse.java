package pl.zaprogramujzycie.mma.dto.response;

import java.util.List;

public record MedicinesResponse(

        List<MedicineResponse> medicines

) {
}
