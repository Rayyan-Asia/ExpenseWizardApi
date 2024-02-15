package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.domain.models.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    Optional<Project> findById(String Id);
    Project save(Project project);
    void delete(Project project);
    Optional<Project> findProjectByNameAndUser(String name, String userId);
    List<Project> findProjectsByUserId(String userId);
}