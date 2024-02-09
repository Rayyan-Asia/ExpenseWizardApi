package Rayyan.Asia.ExpenseWizard.representation.controllers;

import Rayyan.Asia.ExpenseWizard.application.dto.models.project.ProjectDto;
import Rayyan.Asia.ExpenseWizard.application.dto.models.user.CustomUserDetails;
import Rayyan.Asia.ExpenseWizard.application.mappers.ProjectMapper;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ProjectService;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final ProjectMapper projectMapper;

    @GetMapping()
    public ResponseEntity<ProjectDto> get(@RequestParam String projectId) {
        var project = projectService.findById(projectId);
        return project.map(value -> new ResponseEntity<>(projectMapper.domainToDto(value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<ProjectDto> post(@RequestBody @Valid ProjectDto project){
        var authentication = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userService.getById(authentication.getUserId());
        if (user.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (projectService.findProjectByNameAndUser(project.getName(), user.get().getId()).isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        var projectDto = projectService.save(project, user.get().getId());

        return new ResponseEntity<>(projectDto, HttpStatus.CREATED);
    }

    @GetMapping("byUser")
    public ResponseEntity<List<ProjectDto>> getAllProjectsByUser(){
        var authentication = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var projects = projectService.findProjectsByUser(authentication.getUserId());
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<ProjectDto> update(@RequestBody @Valid ProjectDto project){
        var authentication = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (project.getId() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (!projectService.isProjectOwnedByUser(project.getId() ,authentication.getUserId()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        var projectDto = projectService.save(project, authentication.getUserId());
        return new ResponseEntity<>(projectDto, HttpStatus.NO_CONTENT);
    }
}