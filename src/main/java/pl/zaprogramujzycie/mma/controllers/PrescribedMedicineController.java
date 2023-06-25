package pl.zaprogramujzycie.mma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.zaprogramujzycie.mma.dto.request.PrescribedMedicineRequest;
import pl.zaprogramujzycie.mma.dto.response.PrescribedMedicinesResponse;
import pl.zaprogramujzycie.mma.dto.response.PrescribedMedicineResponse;
import pl.zaprogramujzycie.mma.exceptions.NotFoundException;
import pl.zaprogramujzycie.mma.services.PrescribedMedicineService;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/families/{familyId}/familyMembers/{familyMemberId}/prescriptions/{prescriptionId}/prescribedMedicines")
public class PrescribedMedicineController {

    private final PrescribedMedicineService service;

    public PrescribedMedicineController(final PrescribedMedicineService service) {
        this.service = service;
    }


    @Operation(
            description = "Returns all registered prescribed PrescribedMedicines for user",
            tags = "PrescribedMedicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prescribed prescribed medicine found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    ResponseEntity<PrescribedMedicinesResponse> findAll(final Principal principal, final Pageable pageable, @PathVariable final long familyId, @PathVariable final long prescriptionId) {
        System.out.println("---controller---");
        return ResponseEntity.ok(service.findAll(principal, pageable, familyId, prescriptionId));
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
    ResponseEntity<PrescribedMedicineResponse> createPrescribedMedicine(final Principal principal, @RequestBody final PrescribedMedicineRequest prescribedMedicineRequest) {
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
    ResponseEntity<PrescribedMedicineResponse> findById(final Principal principal, @PathVariable final long id, @PathVariable final long familyId, @PathVariable final long prescriptionId) throws NotFoundException {
        return ResponseEntity.ok(service.findById(id, principal, familyId, prescriptionId));
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
    @PutMapping("/{id}")
    ResponseEntity<PrescribedMedicineResponse> updatePrescribedMedicine(final Principal principal, @PathVariable final long id, @RequestBody final PrescribedMedicineRequest prescribedMedicineRequest) {
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
