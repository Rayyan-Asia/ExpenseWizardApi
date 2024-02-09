package Rayyan.Asia.ExpenseWizard.representation.controllers;

import Rayyan.Asia.ExpenseWizard.application.dto.models.user.CustomUserDetails;
import Rayyan.Asia.ExpenseWizard.application.dto.models.user.UserDto;
import Rayyan.Asia.ExpenseWizard.application.dto.models.user.UserUpsertDto;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @PutMapping("update")
    public ResponseEntity<String> register(@Valid @RequestBody UserUpsertDto userDto) {
        var authentication = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userService.getById(authentication.getUserId());
        if (user.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        userDto.setId(user.get().getId());
        userService.save(userDto);
        return new ResponseEntity<>("User Updated Successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<UserDto> get(){
        var authentication = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userService.getById(authentication.getUserId());
        return user.map(userDto -> new ResponseEntity<>(userDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
