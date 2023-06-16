package pl.zaprogramujzycie.mma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.zaprogramujzycie.mma.dto.request.MedicineRequest;
import pl.zaprogramujzycie.mma.dto.request.PrescribedMedicineRequest;
import pl.zaprogramujzycie.mma.dto.response.MedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.PrescribedMedicinesResponse;
import pl.zaprogramujzycie.mma.dto.response.PrescribedMedicineResponse;

import java.security.Principal;

@RestController
@RequestMapping("/prescribedPrescribedMedicines")
public class PrescribedMedicinesController {

    @Operation(
            description = "Returns all registered prescribed PrescribedMedicines for user",
            tags = "PrescribedMedicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prescribed prescribed medicine found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{page}/{size}/{sort}")
    ResponseEntity<PrescribedMedicinesResponse> findAll(@AuthenticationPrincipal final Principal principal, @PathVariable final int page, @PathVariable final int size, @PathVariable final String sort) {
        return null;
    }

    @Operation(
            description = "Create a new prescribed medicine and connect it with user",
            tags = "PrescribedMedicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Prescribed medicine created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<PrescribedMedicineResponse> createPrescribedMedicine(@AuthenticationPrincipal final Principal principal, @RequestBody final PrescribedMedicineRequest prescribedMedicineRequest) {
        return null;
    }

    @Operation(
            description = "Get prescribed medicine by Id",
            tags = "PrescribedMedicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prescribed medicine found"),
            @ApiResponse(responseCode = "404", description = "Prescribed medicine not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    ResponseEntity<PrescribedMedicineResponse> findById(@AuthenticationPrincipal final Principal principal, @PathVariable final long id) {
        return null;
    }

    @Operation(
            description = "Update a prescribed medicine by Id",
            tags = "PrescribedMedicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prescribed medicine updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "prescribed medicine not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}")
    ResponseEntity<PrescribedMedicineResponse> updatePrescribedMedicine(@AuthenticationPrincipal final Principal principal, @PathVariable final long id, @RequestBody final PrescribedMedicineRequest prescribedMedicineRequest) {
        return null;
    }

    @Operation(
            description = "Delete prescribed medicine by Id",
            tags = "PrescribedMedicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Prescribed medicine deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Prescribed medicine not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> deletePrescribedMedicine(@AuthenticationPrincipal final Principal principal, @PathVariable final long id) {
        return null;
    }
}
