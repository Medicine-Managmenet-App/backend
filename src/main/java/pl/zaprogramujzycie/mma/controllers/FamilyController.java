package pl.zaprogramujzycie.mma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.zaprogramujzycie.mma.dto.request.FamilyRequest;
import pl.zaprogramujzycie.mma.dto.response.FamilyResponse;
import pl.zaprogramujzycie.mma.exceptions.NotFoundException;
import pl.zaprogramujzycie.mma.services.FamilyService;

import java.security.Principal;
@CrossOrigin
@RestController
@RequestMapping("/families")
public class FamilyController {

    private final FamilyService familyService;

    private FamilyController(FamilyService familyService) {
        this.familyService = familyService;
    }

    @Operation(
            description = "Create new family",
            tags = "Families"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Family created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)

    //ToDo we don't need create endpoint, creation of the family goes only in the background
    ResponseEntity<FamilyResponse> createFamily(@RequestBody final FamilyRequest newFamilyRequest) {
        return null;
    }

    @Operation(
            description = "Update family",
            tags = "Families"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Family updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Family not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    ResponseEntity<FamilyResponse> updateFamily(Principal principal, @PathVariable final long id, @RequestBody final FamilyRequest request) throws NotFoundException {
        familyService.addUserToTheFamily(id, request, principal);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Get family by Id",
            tags = "Families"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Family found"),
            @ApiResponse(responseCode = "404", description = "Family not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    ResponseEntity<FamilyResponse> findById(@PathVariable final long id, Principal principal) throws NotFoundException {
        return ResponseEntity.ok(familyService.findById(principal,  id));
    }

    //ToDo we don't need create endpoint, deletion of the family goes only in the background
    @Operation(
            description = "Delete family member",
            tags = "Families"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Family deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Family not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> deleteFamily(@PathVariable final long id) {
        return null;
    }
}
