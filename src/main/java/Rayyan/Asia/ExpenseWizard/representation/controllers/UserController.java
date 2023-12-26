package Rayyan.Asia.ExpenseWizard.representation.controllers;

import Rayyan.Asia.ExpenseWizard.application.dto.users.UserDto;
import Rayyan.Asia.ExpenseWizard.application.mappers.UserMapper;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.UserService;
import Rayyan.Asia.ExpenseWizard.domain.models.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final UserMapper mapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.mapper = userMapper;
    }

    @Operation(summary = "Get user by email", description = "Retrieve user details by email")
    @GetMapping("/{email}")
    @ApiResponse(responseCode = "200", description = "User found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
    })
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserDto> getUserByEmail(
            @Parameter(description = "Email address of the user")
            @Email(message = "Invalid email address") @Valid @PathVariable String email) {
        var user = userService.getByEmail(email);
        return user.map(value -> ResponseEntity.ok(mapper.domainToDto(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new user", description = "Add a new user to the system")
    @PostMapping("/create")
    @ApiResponse(responseCode = "201", description = "User created", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
    })
    public ResponseEntity<UserDto> createUser(
            @Parameter(description = "User object to be created") @Valid @RequestBody UserDto user) {
        var newUser = userService.save(mapper.dtoToDomain(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.domainToDto(newUser));
    }
}
