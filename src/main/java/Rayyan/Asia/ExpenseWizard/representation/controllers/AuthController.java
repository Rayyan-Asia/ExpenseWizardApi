package Rayyan.Asia.ExpenseWizard.representation.controllers;

import Rayyan.Asia.ExpenseWizard.application.dto.auth.AuthResponseDto;
import Rayyan.Asia.ExpenseWizard.application.dto.auth.LoginDto;
import Rayyan.Asia.ExpenseWizard.application.dto.auth.RegisterDto;
import Rayyan.Asia.ExpenseWizard.application.mappers.UserMapper;
import Rayyan.Asia.ExpenseWizard.application.security.JWTGenerator;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.UserRepository;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private UserMapper mapper;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder, UserMapper mapper, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
        this.jwtGenerator = jwtGenerator;
    }


    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
        if (userService.getByEmail(registerDto.email).isPresent())
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);

        var user = mapper.dtoToDomain(registerDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);

        return new ResponseEntity<>("User Registration Success", HttpStatus.OK);
    }
}
