package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.domain.models.Project;

import java.util.Optional;

public interface ProjectService {
    public Optional<Project> findById(String id);

    public Project save(Project project);

    Optional<Project> findProjectByNameAndUser(String name, String userId);
}
