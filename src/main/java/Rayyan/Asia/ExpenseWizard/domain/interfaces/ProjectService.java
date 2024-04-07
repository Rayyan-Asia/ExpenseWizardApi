package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.application.dto.models.project.ProjectDto;
import Rayyan.Asia.ExpenseWizard.application.dto.models.project.ProjectListDto;
import Rayyan.Asia.ExpenseWizard.domain.models.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    Optional<ProjectDto> findById(String id);

    ProjectDto save(ProjectDto project, String Id);
    void delete(String projectId);

    Optional<ProjectDto> findProjectByNameAndUser(String name, String userId);

    Page<ProjectDto> findProjectsByUser(String id, Pageable pageable);

    boolean isProjectOwnedByUser(String projectId, String userId);

    Page<ProjectListDto> getProjectsWithCurrentMonthExpenses(String userId, Pageable pageable);
}
