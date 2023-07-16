package pl.zaprogramujzycie.mma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zaprogramujzycie.mma.dto.request.PrescriptionRequest;
import pl.zaprogramujzycie.mma.dto.response.PrescriptionResponse;
import pl.zaprogramujzycie.mma.exceptions.NotFoundException;
import pl.zaprogramujzycie.mma.services.PrescriptionService;

import java.net.URI;
import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/families/{familyId}/familyMembers/{familyMemberId}/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

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
    public ResponseEntity<PrescriptionResponse> createPrescription(final Principal principal,
                                                                   @RequestBody final PrescriptionRequest prescriptionRequest,
                                                                   @PathVariable final long familyMemberId,
                                                                   @PathVariable final long familyId) throws NotFoundException{
       PrescriptionResponse response = prescriptionService.save(prescriptionRequest, principal, familyMemberId, familyId);
       return ResponseEntity.created(URI.create("/families/" + familyId +
               "/familyMembers/" + familyMemberId + "/prescriptions/"
               + response.id())).body(response);
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
    public ResponseEntity<PrescriptionResponse> getPrescription(final Principal principal,
                                                                @PathVariable final long id,
                                                                @PathVariable final long familyId,
                                                                @PathVariable final long familyMemberId) throws NotFoundException {
        return ResponseEntity.ok(prescriptionService.findById(principal, id, familyMemberId, familyId));
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
    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionResponse> updatePrescriptions(final Principal principal,
                                                                    @RequestBody final PrescriptionRequest prescriptionRequest,
                                                                    @PathVariable final long id,
                                                                    @PathVariable final long familyMemberId,
                                                                    @PathVariable final long familyId) throws NotFoundException {
        prescriptionService.partialUpdate(id, prescriptionRequest, principal, familyMemberId, familyId);
        return ResponseEntity.noContent().build();
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
    public ResponseEntity<String> deletePrescriptions(final Principal principal,
                                                      @PathVariable final long id,
                                                      @PathVariable final long familyMemberId,
                                                      @PathVariable final long familyId) throws NotFoundException {
        prescriptionService.deleteById(id, principal, familyMemberId, familyId);
        return ResponseEntity.noContent().build();
    }

}
