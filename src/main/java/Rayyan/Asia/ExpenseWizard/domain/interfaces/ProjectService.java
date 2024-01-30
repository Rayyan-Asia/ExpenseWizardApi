package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.domain.models.Project;

import java.util.Optional;

public interface ProjectService {
    Optional<Project> findById(String id);

    Project save(Project project);

    Optional<Project> findProjectByNameAndUser(String name, String userId);
}
