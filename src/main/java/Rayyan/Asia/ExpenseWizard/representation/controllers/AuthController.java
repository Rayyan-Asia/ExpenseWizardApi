package Rayyan.Asia.ExpenseWizard.representation.controllers;

import Rayyan.Asia.ExpenseWizard.application.messages.auth.LoginDto;
import Rayyan.Asia.ExpenseWizard.application.dto.models.user.CustomUserDetails;
import Rayyan.Asia.ExpenseWizard.application.dto.models.user.UserDto;
import Rayyan.Asia.ExpenseWizard.application.security.JwtUtil;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
        var userFound = userService.getByEmail(loginDto.getEmail());
        var customUserDetails = new CustomUserDetails(loginDto.getEmail(), loginDto.getPassword());
        if (userFound.isPresent()) {
            return new ResponseEntity<>("Authentication Successful: " + JwtUtil.generateToken(customUserDetails), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("User not Found or Access Denied, Incorrect Credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDto registerDto) {
        if (userService.getByEmail(registerDto.getEmail()).isPresent())
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        userService.save(registerDto);
        return new ResponseEntity<>("User Registration Success", HttpStatus.OK);
    }
}
