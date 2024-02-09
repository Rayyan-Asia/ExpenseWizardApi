package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.application.dto.models.project.ProjectDto;
import Rayyan.Asia.ExpenseWizard.domain.models.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    Optional<Project> findById(String id);

    ProjectDto save(ProjectDto project, String Id);

    Optional<ProjectDto> findProjectByNameAndUser(String name, String userId);

    List<ProjectDto> findProjectsByUser(String id);

    boolean isProjectOwnedByUser(String projectId, String userId);
}
