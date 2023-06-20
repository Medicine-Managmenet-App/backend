package pl.zaprogramujzycie.mma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import pl.zaprogramujzycie.mma.dto.request.UserRequest;
import pl.zaprogramujzycie.mma.dto.response.UserResponse;
import pl.zaprogramujzycie.mma.entities.User;
import pl.zaprogramujzycie.mma.repositories.UserRepository;

import java.security.Principal;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Profile("prod")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
    ResponseEntity<UserResponse> createUser(@RequestBody final UserRequest newUserRequest) {
        //TODO: CHANGE IT WHEN SERVICE WAS CREATED
        userRepository.save(User.builder()
                .login("admin")
                .password(passwordEncoder.encode("tajnedane123!"))
                .build());
        return null;
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
    public ResponseEntity<UserResponse> findById(@PathVariable final long id) {
        return null;
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
    @PatchMapping("/{name}")
    private ResponseEntity<Void> updateUserName(@AuthenticationPrincipal final Principal principal, @PathVariable final String name) {
        return null;
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
    @PatchMapping("/{password}")
    private ResponseEntity<Void> updateUserPassword(@AuthenticationPrincipal final Principal principal, @PathVariable final String password) {
        return null;
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
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public static ResponseEntity<Void> deleteUser(@AuthenticationPrincipal final Principal principal) {
        return null;
    }
}
