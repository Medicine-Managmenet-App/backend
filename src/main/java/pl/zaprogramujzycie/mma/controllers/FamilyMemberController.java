package pl.zaprogramujzycie.mma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import pl.zaprogramujzycie.mma.dto.request.FamilyMemberRequest;
import pl.zaprogramujzycie.mma.dto.response.FamilyMemberResponse;
import pl.zaprogramujzycie.mma.dto.response.FamilyMembersResponse;

@RestController
@RequestMapping("/familyMembers")
public class FamilyMemberController {

    @Operation(
            description = "Returns all registered family members",
            tags = "FamilyMembers"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "FamilyMembers found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{page}/{size}/{sort}")
    ResponseEntity<FamilyMembersResponse> findAll(@PathVariable final int page, @PathVariable final int size, @PathVariable final String sort) {
        return null;
    }

    @Operation(
            description = "Create new family member",
            tags = "FamilyMembers"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "FamilyMember created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<FamilyMemberResponse> createFamilyMember(@RequestBody final FamilyMemberRequest newFamilyMemberRequest) {
        return null;
    }

    @Operation(
            description = "Get family member by Id",
            tags = "FamilyMembers"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "FamilyMember found"),
            @ApiResponse(responseCode = "404", description = "FamilyMember not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    ResponseEntity<FamilyMemberResponse> findById(@PathVariable final long id) {
        return null;
    }


    @Operation(
            description = "Update family member",
            tags = "FamilyMembers"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "FamilyMember updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "FamilyMember not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}")
    ResponseEntity<FamilyMemberResponse> updateFamilyMember(@PathVariable final long id, @RequestBody final FamilyMemberRequest familyMemberDto) {
        return null;
    }

    @Operation(
            description = "Delete family member",
            tags = "FamilyMembers"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "FamilyMember deleted successfully"),
            @ApiResponse(responseCode = "404", description = "FamilyMember not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> deleteFamilyMember(@PathVariable final long id) {
        return null;
    }


}
