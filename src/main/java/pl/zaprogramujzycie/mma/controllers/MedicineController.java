package pl.zaprogramujzycie.mma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import pl.zaprogramujzycie.mma.dto.response.MedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.MedicinesResponse;

import java.util.List;
@RestController
@RequestMapping("/medicines")
public class MedicineController {

    @Operation(
            description = "Returns all registered medicines",
            tags = "Medicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Medicine found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{page}/{size}/{sort}")
    ResponseEntity<MedicinesResponse> findAll(@PathVariable final int page, @PathVariable final int size, @PathVariable final String sort) {
        return null;
    }

    @Operation(
            description = "Create a new medicine",
            tags = "Medicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Medicine created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<MedicineResponse> createMedicine(@RequestBody final MedicineRequest newMedicineRequest) {
        return null;
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
    ResponseEntity<MedicineResponse> findById(@PathVariable final long id) {
            return null;
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
    @PatchMapping("/{id}")
    ResponseEntity<MedicineResponse> updateMedicine(@PathVariable final long id, @RequestBody final MedicineRequest MedicineDto) {
        return null;
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
    ResponseEntity<Void> deleteMedicine(@PathVariable final long id) {
        return null;
    }
}
