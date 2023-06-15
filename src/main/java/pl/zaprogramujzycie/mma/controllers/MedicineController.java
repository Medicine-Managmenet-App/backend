package pl.zaprogramujzycie.mma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.zaprogramujzycie.mma.DTO.MedicineDTO;
import pl.zaprogramujzycie.mma.DTO.PrescribedMedicinesDTO;
import pl.zaprogramujzycie.mma.entity.Medicine;
import pl.zaprogramujzycie.mma.repositories.MedicineRepository;
import pl.zaprogramujzycie.mma.services.MedicineService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Tag(name ="Medicine", description = "Medicine management APIs")
@RestController
@RequestMapping("/medicines")
public class MedicineController {

    private MedicineService service;

    MedicineDTO dto;

    @Operation(
            // operationId = "getMedicines",
            summary = "get all medicines",
            description = "returns all registered medicines",
            tags = "Medicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Medicine found", ref = "#/components/schemas/Medicine"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<MedicineDTO>> findAll(Pageable pageable) {
        return null;
    }

    @Operation(
            operationId = "createMedicine",
            description = "create a new medicine",
            tags = "Medicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Medicine created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
     ResponseEntity<MedicineDTO> createMedicine(@RequestBody MedicineDTO newMedicineRequest) {
        return null;
    }

    @Operation(
            operationId = "getMedicineById",
            description = "get medicine by Id",
            tags = "Medicine"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Medicine found", ref = "#/components/schemas/Medicine"),
            @ApiResponse(responseCode = "404", description = "Medicine not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MedicineDTO> findById(@PathVariable long id) {
            return null;
    }


    @Operation(
            operationId = "Update a medicine by Id",
            description = "updateMedicine",
            tags = "Medicine"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Medicine updated successfully", ref = "#/components/schemas/Medicine"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Medicine not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}")
    private ResponseEntity<MedicineDTO> updateMedicine(@PathVariable long id, @RequestBody MedicineDTO MedicineDto) {
        return null;
    }

    @Operation(
            operationId = "Delete medicine by Id",
            description = "deleteMedicine",
            tags = "Medicine"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Medicine deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Medicine not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteMedicine(@PathVariable long id) {
        return null;
    }
}