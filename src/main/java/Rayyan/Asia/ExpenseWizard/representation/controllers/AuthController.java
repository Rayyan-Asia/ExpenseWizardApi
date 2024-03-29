package Rayyan.Asia.ExpenseWizard.representation.controllers;

import Rayyan.Asia.ExpenseWizard.application.dto.models.user.UserContactDto;
import Rayyan.Asia.ExpenseWizard.application.dto.models.user.UserUpsertDto;
import Rayyan.Asia.ExpenseWizard.application.messages.auth.LoginDto;
import Rayyan.Asia.ExpenseWizard.application.dto.models.user.CustomUserDetails;
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
    public ResponseEntity<UserContactDto> login(@Valid @RequestBody LoginDto loginDto) {
        var userFound = userService.getByEmail(loginDto.getEmail());
        if (userFound.isPresent()) {
            var customUserDetails = new CustomUserDetails(userFound.get().getId(), loginDto.getPassword());
            return new ResponseEntity<>(new UserContactDto("Authentication Successful: " + JwtUtil.generateToken(customUserDetails), userFound.get()), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(new UserContactDto("User not Found or Access Denied, Incorrect Credentials", null), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("register")
    public ResponseEntity<UserContactDto> register(@Valid @RequestBody UserUpsertDto registerDto) {
        var userFound = userService.getByEmail(registerDto.getEmail());
        if (userFound.isPresent())
            return new ResponseEntity<>(new UserContactDto("Username is taken", null), HttpStatus.BAD_REQUEST);
        var user = userService.save(registerDto);
        var customUserDetails = new CustomUserDetails(user.getId(), registerDto.getPassword());
        return new ResponseEntity<>(new UserContactDto("Authentication Successful: " + JwtUtil.generateToken(customUserDetails), user), HttpStatus.ACCEPTED);
    }
}
