package pl.zaprogramujzycie.mma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.zaprogramujzycie.mma.DTO.UserDTO;
import pl.zaprogramujzycie.mma.services.UserService;



@RestController
@RequestMapping("/users")
public class UserController {


    private UserService service;


    @Operation(
            operationId = "createUser",
            description = "create new user",
            tags = "Users"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    ResponseEntity<Void> createUser(@RequestBody UserDTO newUserRequest, UriComponentsBuilder ucb) {
        return null;
    }

    @Operation(
            operationId = "getUserById",
            description = "get user by Id",
            tags = "User"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found", ref = "#/components/schemas/Users"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable long id) {
        return null;
    }

    @Operation(
            operationId = "Update a User by Id",
            description = "updateUser",
            tags = "User"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User updated successfully", ref = "#/components/schemas/User"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("users/{id}")
    private ResponseEntity<Void> updateUser(@PathVariable long id, @RequestBody UserDTO dto) {
        return null;
    }

    //ToDo create separate method for password update
    @Operation(
            operationId = "Delete user by Id",
            description = "deleteUser",
            tags = "User"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public static ResponseEntity<Long> deleteUser(@PathVariable long id) {
        return null;
    }
}
