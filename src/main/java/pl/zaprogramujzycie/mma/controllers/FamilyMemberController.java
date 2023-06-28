package pl.zaprogramujzycie.mma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zaprogramujzycie.mma.dto.request.FamilyMemberRequest;
import pl.zaprogramujzycie.mma.dto.response.FamilyMemberResponse;
import pl.zaprogramujzycie.mma.dto.response.FamilyMembersResponse;
import pl.zaprogramujzycie.mma.exceptions.NotFoundException;
import pl.zaprogramujzycie.mma.services.FamilyMemberService;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/families/{familyId}/familyMembers")
public class FamilyMemberController {

    private final FamilyMemberService familyMemberService;

    FamilyMemberController(FamilyMemberService familyMemberService) {
        this.familyMemberService = familyMemberService;
    }

    @Operation(
            description = "Returns all registered family members",
            tags = "FamilyMembers"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "FamilyMembers found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    ResponseEntity<FamilyMembersResponse> findAll(final Pageable pageable, final Principal principal, @PathVariable final long familyId) throws NotFoundException{
        return ResponseEntity.ok(familyMemberService.findAll(principal, pageable, familyId));
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
    ResponseEntity<FamilyMemberResponse> createFamilyMember(Principal principal,
                                                            @RequestBody final FamilyMemberRequest newFamilyMemberRequest,
                                                            @PathVariable final long familyId) throws NotFoundException{
        FamilyMemberResponse response = familyMemberService.save(newFamilyMemberRequest, principal, familyId);
        System.out.println("response in controller: " + response.getId());
        return ResponseEntity.created(URI.create("/families/" + familyId +"/familyMembers/" + response.getId())).body(response);
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
    ResponseEntity<FamilyMemberResponse> findById(Principal principal, @PathVariable final long id, @PathVariable final long familyId) throws NotFoundException {
        System.out.println("-------in controller---------");
        return ResponseEntity.ok(familyMemberService.findById(principal, id, familyId));
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
    @PutMapping("/{id}")
    ResponseEntity<FamilyMemberResponse> updateFamilyMember(Principal principal,
                                                            @PathVariable final long id,
                                                            @PathVariable final long familyId,
                                                            @RequestBody final FamilyMemberRequest request) throws NotFoundException {
        familyMemberService.update(id, request, principal, familyId);
        return ResponseEntity.noContent().build();
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
    ResponseEntity<Void> deleteFamilyMember(Principal principal,
                                            @PathVariable final long id,
                                            @PathVariable final long familyId) throws NotFoundException {
        familyMemberService.deleteById(id, principal, familyId);
        return ResponseEntity.noContent().build();
    }



}
