package Rayyan.Asia.ExpenseWizard.representation.controllers;

import Rayyan.Asia.ExpenseWizard.application.dto.models.ProjectDto;
import Rayyan.Asia.ExpenseWizard.application.mappers.ProjectMapper;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ProjectService;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final UserService userService;
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    public ProjectController(UserService userService, ProjectService projectService, ProjectMapper projectMapper) {
        this.userService = userService;
        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    @GetMapping()
    public ResponseEntity<ProjectDto> register(@RequestParam String projectId) {
        var project = projectService.findById(projectId);
        return project.map(value -> new ResponseEntity<>(projectMapper.domainToDto(value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}