package pl.zaprogramujzycie.mma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.zaprogramujzycie.mma.DTO.UserDTO;
import pl.zaprogramujzycie.mma.entity.User;
import pl.zaprogramujzycie.mma.repositories.UserRepository;
import pl.zaprogramujzycie.mma.utils.UserService;


import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController

@RequestMapping("/users")
public class UserController {

    private static UserRepository userRepository;

    private UserService service;

    UserDTO dto;

    UserController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Operation(
            operationId = "createUser",
            description = "create new user",
            tags = "Users"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PostMapping
    ResponseEntity<Void> createUser(@RequestBody UserDTO newUserRequest, UriComponentsBuilder ucb) {
        // ToDo: This part should be refactored, to apply Single Responsibility Rule
        User newUser = new User(newUserRequest.getLogin(), newUserRequest.getPassword());
        User savedUser = userRepository.save(newUser);

        URI locationOfNewUser = ucb
                .path("users/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewUser).build();
    }

    @Operation(
            operationId = "getUserById",
            description = "get user by Id",
            tags = "User"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found", ref = "#/components/schemas/Users"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @GetMapping("users/{requestedId}")
    public ResponseEntity<User> findById(@PathVariable Long requestedId) {
        Optional<User> userOptional = userRepository.findById(requestedId);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
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
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PatchMapping("users/{requestedId}")
    private ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody UserDTO dto, UriComponentsBuilder ucb) {
        service.partialUpdate(id, dto.getLogin(), dto.getPassword());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
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
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @DeleteMapping("users/{requestedId}")
    public static ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        int response = HttpStatus.NO_CONTENT.value();
        try {
            Optional<User> user = userRepository.findById(id);
            userRepository.delete(user.get());
        } catch (NoSuchElementException e) {
            System.out.println("catch exception");
            response = HttpStatus.NOT_FOUND.value();
        }
        return ResponseEntity.status(response).build();
    }
}
