package pl.zaprogramujzycie.mma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.zaprogramujzycie.mma.DTO.MedicineDTO;
import pl.zaprogramujzycie.mma.entity.Medicine;
import pl.zaprogramujzycie.mma.repositories.MedicineRepository;
import pl.zaprogramujzycie.mma.utils.MedicineService;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController

@RequestMapping("/medicines")
public class MedicineController {

    private static MedicineRepository medicineRepository;

    private MedicineService service;

    MedicineDTO dto;

    MedicineController(final MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    @Operation(
            operationId = "getMedicines",
            description = "returns all registered medicines",
            tags = "Medicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Medicine found", ref = "#/components/schemas/Medicine"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @GetMapping
    public ResponseEntity<List<Medicine>> findAll(Pageable pageable) {
        Page<Medicine> page = medicineRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "expirationDate"))));
        return ResponseEntity.ok(page.getContent());
    }

    @Operation(
            operationId = "createMedicine",
            description = "create a new medicine",
            tags = "Medicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Medicine created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PostMapping
     ResponseEntity<Void> createMedicine(@RequestBody MedicineDTO newMedicineRequest, UriComponentsBuilder ucb) {
        // ToDo: This part should be refactored, to apply Single Responsibility Rule
        Medicine newMedicine = new Medicine(newMedicineRequest.getName(), newMedicineRequest.getExpirationDate(),
                newMedicineRequest.getPeriodAfterOpening());
        Medicine savedMedicine = medicineRepository.save(newMedicine);

        URI locationOfNewMedicine = ucb
                .path("medicines/{id}")
                .buildAndExpand(savedMedicine.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewMedicine).build();
    }

    @Operation(
            operationId = "getMedicineById",
            description = "get medicine by Id",
            tags = "Medicine"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Medicine found", ref = "#/components/schemas/Medicine"),
            @ApiResponse(responseCode = "404", description = "Medicine not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @GetMapping("medicines/{requestedId}")
    public ResponseEntity<Medicine> findById(@PathVariable Long requestedId) {
        Optional<Medicine> medicineOptional = medicineRepository.findById(requestedId);
        if (medicineOptional.isPresent()) {
            return ResponseEntity.ok(medicineOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
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
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PatchMapping("medicines/{requestedId}")
    private ResponseEntity<Void> updateMedicine(@PathVariable Long id, @RequestBody MedicineDTO dto, UriComponentsBuilder ucb) {
        service.partialUpdate(id, dto.getName(), dto.getExpirationDate(), dto.getPeriodAfterOpening());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    @Operation(
            operationId = "Delete medicine by Id",
            description = "deleteMedicine",
            tags = "Medicine"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Medicine deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Medicine not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @DeleteMapping("medicines/{requestedId}")
    public static ResponseEntity<Long> deleteMedicine(@PathVariable Long id) {
        int response = HttpStatus.NO_CONTENT.value();
        try {
            Optional<Medicine> medicine = medicineRepository.findById(id);
            medicineRepository.delete(medicine.get());
        } catch (NoSuchElementException e) {
            System.out.println("catch exception");
            response = HttpStatus.NOT_FOUND.value();
        }
        return ResponseEntity.status(response).build();
    }

    public static void main(String[] args) {
        System.out.println(MedicineController.deleteMedicine(3L));
    }


}