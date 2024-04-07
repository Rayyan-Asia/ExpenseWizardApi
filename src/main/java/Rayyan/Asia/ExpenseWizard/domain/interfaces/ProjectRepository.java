package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.domain.models.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    Optional<Project> findById(String Id);
    Project save(Project project);
    void delete(Project project);
    Optional<Project> findProjectByNameAndUser(String name, String userId);
    Page<Project> findProjectsByUserId(String userId, Pageable pageable);

    Page<Project> findProjectsWithCurrentMonthExpenses(String userId, Pageable pageable);
}