package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.application.dto.models.ProjectDto;
import Rayyan.Asia.ExpenseWizard.domain.models.Project;

import java.util.Optional;

public interface ProjectService {
    Optional<Project> findById(String id);

    ProjectDto save(ProjectDto project);

    Optional<ProjectDto> findProjectByNameAndUser(String name, String userId);
}
