package pl.zaprogramujzycie.mma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.zaprogramujzycie.mma.DTO.PrescribedMedicinesDTO;
import pl.zaprogramujzycie.mma.services.PrescribedMedicinesService;

import java.net.URI;


@RestController
@RequestMapping("familyMembers/{id}")
public class PrescribedMedicinesController {

    @Autowired
    private PrescribedMedicinesService prescribedMedicinesService;

    @Operation(
            operationId = "createPrescribedMedicines",
            description = "create new list of prescribed medicines, connect it with FamilyMember",
            tags = "PrescribedMedicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "PrescribedMedicines created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PostMapping
    public ResponseEntity<PrescribedMedicinesDTO> createPrescribedMedicines(@PathVariable long familyMemberId, @RequestBody PrescribedMedicinesDTO prescribedMedicinesDTO,
                                                                      UriComponentsBuilder ucb){
        return null;
    }

    @Operation(
            operationId = "getPrescribedMedicines",
            description = "get PrescribedMedicines connected with user Id",
            tags = "PrescribedMedicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "PrescribedMedicines found", ref = "#/components/schemas/PrescribedMedicines"),
            @ApiResponse(responseCode = "404", description = "PrescribedMedicines not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @GetMapping("/prescription")
    public ResponseEntity<PrescribedMedicinesDTO> getPrescribedMedicines(@PathVariable long requestedId) {
        return null;
    }

    @Operation(
            operationId = "updatePrescribedMedicines",
            description = "add or remove medicine from list",
            tags = "PrescribedMedicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "PrescribedMedicines updated successfully", ref = "#/components/schemas/PrescribedMedicines"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "PrescribedMedicines not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PatchMapping("/prescription")
    public ResponseEntity<PrescribedMedicinesDTO> updatePrescribedMedicines(@PathVariable("familyMemberId") long familyMemberId,
                                                                            @RequestBody PrescribedMedicinesDTO prescribedMedicinesDTO){
        return null;
    }
    @Operation(
            operationId = "deletePrescribedMedicines",
            description = "delete Prescribed Medicines",
            tags = "PrescribedMedicines"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "PrescribedMedicines deleted successfully"),
            @ApiResponse(responseCode = "404", description = "PrescribedMedicines not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @DeleteMapping("/prescription")
    public ResponseEntity<String> deletePrescribedMedicines(@PathVariable("familyMemberId") long familyMemberId){
        return null;
    }

}
