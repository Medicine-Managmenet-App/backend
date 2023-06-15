package pl.zaprogramujzycie.mma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.zaprogramujzycie.mma.DTO.FamilyMemberDTO;

import java.util.List;

public class FamilyMemberController {

    // private FamilyMemberService service;

    // FamilyMemberDTO dto;


    @Operation(
            operationId = "getFamilyMembers",
            description = "returns all registered family members",
            tags = "FamilyMembers"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "FamilyMembers found", ref = "#/components/schemas/FamilyMember"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<FamilyMemberDTO>> findAll(Pageable pageable) {
        return null;
    }

    @Operation(
            operationId = "createFamilyMember",
            description = "create new family member",
            tags = "FamilyMembers"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "FamilyMember created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    ResponseEntity<FamilyMemberDTO> createFamilyMember(@RequestBody FamilyMemberDTO newFamilyMemberRequest, UriComponentsBuilder ucb) {
        return null;
    }

    @Operation(
            operationId = "getFamilyMemberById",
            description = "get family member by Id",
            tags = "FamilyMember"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "FamilyMember found", ref = "#/components/schemas/FamilyMember"),
            @ApiResponse(responseCode = "404", description = "FamilyMember not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("familymembers/{id}")
    public ResponseEntity<FamilyMemberDTO> findById(@PathVariable long id) {
        return null;
    }


    @Operation(
            operationId = "updateFamilyMember",
            description = "update family member",
            tags = "FamilyMember"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "FamilyMember updated successfully", ref = "#/components/schemas/FamilyMember"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "FamilyMember not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("familymembers/{id}")
    private ResponseEntity<FamilyMemberDTO> updateFamilyMember(@PathVariable Long id, @RequestBody FamilyMemberDTO familyMemberDto) {
        return null;
    }

    @Operation(
            operationId = "Delete family member by Id",
            description = "deleteFamilyMember",
            tags = "FamilyMember"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "FamilyMember deleted successfully"),
            @ApiResponse(responseCode = "404", description = "FamilyMember not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteFamilyMember(@PathVariable long id) {
        return null;
    }


}
