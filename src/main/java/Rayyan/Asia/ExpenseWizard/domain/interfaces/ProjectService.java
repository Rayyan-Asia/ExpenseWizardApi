package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.application.dto.models.project.ProjectDto;
import Rayyan.Asia.ExpenseWizard.domain.models.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    Optional<ProjectDto> findById(String id);

    ProjectDto save(ProjectDto project, String Id);
    void delete(String projectId);

    Optional<ProjectDto> findProjectByNameAndUser(String name, String userId);

    List<ProjectDto> findProjectsByUser(String id);

    boolean isProjectOwnedByUser(String projectId, String userId);
}
