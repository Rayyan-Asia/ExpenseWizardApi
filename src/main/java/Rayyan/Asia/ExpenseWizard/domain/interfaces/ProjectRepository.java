package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.domain.models.Project;
import Rayyan.Asia.ExpenseWizard.domain.models.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRepository extends CrudRepository<Project, String> {
    @Query("SELECT u FROM Project u WHERE u.name = :name AND u.userEntity.id = :userID")
    Optional<Project> findProjectByNameAndUser(@Param("name") String name, @Param("user") String userId);
}