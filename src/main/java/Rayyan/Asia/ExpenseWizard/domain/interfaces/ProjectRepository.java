package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.domain.models.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends CrudRepository<Project, String> {
    @Query("SELECT u FROM Project u WHERE u.name = :name AND u.user.id = :userId")
    Optional<Project> findProjectByNameAndUser(@Param("name") String name, @Param("userId") String userId);

    @Query("SELECT u FROM Project u WHERE u.user.id = :userId")
    List<Project> findProjectsByUserId(@Param("userId") String userId);
}