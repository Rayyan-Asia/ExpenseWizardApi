package Rayyan.Asia.ExpenseWizard.representation.controllers;

import Rayyan.Asia.ExpenseWizard.application.dto.models.user.*;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ExpenseService;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ProjectService;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;
    private final ProjectService projectService;
    private final ExpenseService expenseService;

    @PutMapping("update")
    public ResponseEntity<UserContactDto> register(@Valid @RequestBody UserUpsertDto userDto) {
        var authentication = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userService.getById(authentication.getUserId());
        if (user.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var existingUser = userService.getByEmail(userDto.getEmail());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(user.get().getId()))
            return new ResponseEntity<>(new UserContactDto("Email already used",null), HttpStatus.BAD_REQUEST);

        userDto.setId(user.get().getId());
        var saved = userService.save(userDto);
        return new ResponseEntity<>(new UserContactDto("User Updated Successfully",saved), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<UserDto> get() {
        var authentication = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userService.getById(authentication.getUserId());
        return user.map(userDto -> new ResponseEntity<>(userDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("download")
    public ResponseEntity<SyncDto> Download() {
        var authentication = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userService.getById(authentication.getUserId());
        if(user.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        var projects = projectService.findProjectsByUser(authentication.getUserId(), Pageable.unpaged());
        var expenses = expenseService.findByUserId(authentication.getUserId(), Pageable.unpaged());
        var sync = new SyncDto();
        sync.setUser(user.get());
        sync.setProjects(projects.stream().toList());
        sync.setExpense(expenses.stream().toList());
        return new ResponseEntity<>(sync, HttpStatus.OK);
    }

    @PostMapping("upload")
    public ResponseEntity upload(SyncDto syncDto) {

        var authentication = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userService.getById(authentication.getUserId());
        if(user.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        var oldProjects = projectService.findProjectsByUser(authentication.getUserId(), Pageable.unpaged());
        var oldExpenses = expenseService.findByUserId(authentication.getUserId(), Pageable.unpaged());

        oldExpenses.forEach(expense -> {
            expenseService.delete(expense.getId());
        });

        oldProjects.forEach(project -> {
            projectService.delete(project.getId());
        });

        projectService.save(syncDto.getProjects(), authentication.getUserId());
        expenseService.save(syncDto.getExpense());
        return ResponseEntity.ok().build();
    }
}
