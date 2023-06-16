package pl.zaprogramujzycie.mma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.zaprogramujzycie.mma.dto.request.PrescriptionRequest;
import pl.zaprogramujzycie.mma.dto.response.PrescriptionResponse;

import java.security.Principal;
// import pl.zaprogramujzycie.mma.services.PrescriptionsService;



@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {

    @Operation(
            description = "Create new list of prescriptions, connect it with user",
            tags = "Prescriptions"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Prescriptions created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PrescriptionResponse> createPrescription(@AuthenticationPrincipal final Principal principal, @RequestBody final PrescriptionRequest prescriptionRequest){
        return null;
    }

    @Operation(
            description = "Get Prescription connected with user Id",
            tags = "Prescriptions"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prescriptions found"),
            @ApiResponse(responseCode = "404", description = "Prescriptions not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionResponse> getPrescription(@AuthenticationPrincipal final Principal principal, @PathVariable final long id) {
        return null;
    }

    @Operation(
            description = "Add or remove medicine from list",
            tags = "Prescriptions"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prescriptions updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Prescriptions not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<PrescriptionResponse> updatePrescriptions(@AuthenticationPrincipal final Principal principal, @PathVariable final long id, @RequestBody final PrescriptionRequest prescriptionRequest){
        return null;
    }
    @Operation(
            description = "Delete prescription",
            tags = "Prescriptions"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Prescriptions deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Prescriptions not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deletePrescriptions(@AuthenticationPrincipal final Principal principal, @PathVariable final long id){
        return null;
    }

}
