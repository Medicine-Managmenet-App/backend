package pl.zaprogramujzycie.mma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.zaprogramujzycie.mma.dto.request.ChangeFamilyRequest;
import pl.zaprogramujzycie.mma.dto.request.UserRequest;
import pl.zaprogramujzycie.mma.dto.response.UserResponse;
import pl.zaprogramujzycie.mma.exceptions.NotFoundException;
import pl.zaprogramujzycie.mma.services.UserService;

import java.net.URI;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
  
    final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(
            description = "Create new user",
            tags = "Users"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody final UserRequest newUserRequest) {
        UserResponse response = service.save(newUserRequest);
        return ResponseEntity.created(URI.create("/users/" + response.id())).body(response);
    }

    @Operation(
            description = "Get user by Id",
            tags = "Users"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(Principal principal, @PathVariable final long id) throws NotFoundException {
        return ResponseEntity.ok(service.findById(principal, id));
    }

    @Operation(
            description = "Find user by login, needed to send invitation for to join family",
            tags = "Users"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<UserResponse> findByLogin(@RequestParam final String login) throws NotFoundException {
        return ResponseEntity.ok(service.findByLogin(login));
    }

    @Operation(
            description = "Update a user name",
            tags = "Users"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> changeConnectedFamily(final Principal principal, @PathVariable final long id, @RequestBody ChangeFamilyRequest family) throws NotFoundException {
        service.changeFamily(id, principal, family);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Update a user password",
            tags = "Users"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}/passwordChange")
    private ResponseEntity<Void> updateUserPassword(final Principal principal, @PathVariable final long id, @RequestBody UserRequest changePasswordRequest) throws NotFoundException {
        service.changePassword(id, principal, changePasswordRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Delete account",
            tags = "Users"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUser(final Principal principal, @PathVariable final long id) throws NotFoundException {
        service.deleteById(id, principal);
        return ResponseEntity.noContent().build();
    }

}
