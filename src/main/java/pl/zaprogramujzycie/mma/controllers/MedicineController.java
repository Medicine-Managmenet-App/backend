package pl.zaprogramujzycie.mma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.zaprogramujzycie.mma.dto.request.MedicineRequest;
import pl.zaprogramujzycie.mma.dto.response.MedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.MedicinesResponse;
import pl.zaprogramujzycie.mma.services.MedicineService;

import java.net.URI;
import java.security.Principal;
import java.util.List;
@RestController
@RequestMapping("/medicines")
public class MedicineController {

    @Autowired
    MedicineService service;

    @Operation(
            description = "Returns all registered medicines for user",
            tags = "Medicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Medicine found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    ResponseEntity<MedicinesResponse> findAll(Pageable pageable, final Principal principal) {
        return ResponseEntity.ok(service.findAll(principal, pageable));
    }


    @Operation(
            description = "Create a new medicine and connect it with user",
            tags = "Medicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Medicine created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<MedicineResponse> createMedicine(final Principal principal, @RequestBody final MedicineRequest newMedicineRequest) {
        return service.save(newMedicineRequest);
    }

    @Operation(
            description = "Get medicine by Id",
            tags = "Medicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Medicine found"),
            @ApiResponse(responseCode = "404", description = "Medicine not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    ResponseEntity<MedicineResponse> findById(final Principal principal, @PathVariable final long id) {
        return service.findById(id, principal);
    }

    @Operation(
            description = "Update a medicine by Id",
            tags = "Medicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Medicine updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Medicine not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    ResponseEntity<MedicineResponse> updateMedicine(final Principal principal, @PathVariable final long id, @RequestBody final MedicineRequest request) {
        return service.partialUpdate(id, request, principal);
    }

    @Operation(
            description = "Delete medicine by Id",
            tags = "Medicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Medicine deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Medicine not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> deleteMedicine( final Principal principal, @PathVariable final long id) {
        return service.deleteById(id, principal);
    }
}
