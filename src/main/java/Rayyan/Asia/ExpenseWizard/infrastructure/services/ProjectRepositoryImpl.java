package Rayyan.Asia.ExpenseWizard.infrastructure.services;

import Rayyan.Asia.ExpenseWizard.domain.interfaces.ProjectRepository;
import Rayyan.Asia.ExpenseWizard.domain.models.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProjectRepositoryImpl implements ProjectRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Optional<Project> findById(String id) {
        return entityManager.createQuery("SELECT DISTINCT e FROM Project e LEFT JOIN FETCH e.expenses WHERE e.id = :id", Project.class)
                .setParameter("id", id)
                .getResultList().stream().findFirst();
    }


    @Override
    public Project save(Project project) {
        if (project.getId() != null)
            entityManager.merge(project);
        else
            entityManager.persist(project);
        return project;
    }

    @Override
    public void delete(Project project) {
        entityManager.createQuery("DELETE FROM Expense e WHERE e.project.id = :id")
                .setParameter("id", project.getId())
                .executeUpdate();
        entityManager.createQuery("DELETE FROM Project e WHERE e.id = :id")
                .setParameter("id", project.getId())
                .executeUpdate();
    }

    @Override
    public Optional<Project> findProjectByNameAndUser(String name, String userId) {
        return entityManager.createQuery("SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.expenses WHERE p.user.id = :id AND p.name = :name", Project.class)
                .setParameter("id", userId)
                .setParameter("name", name)
                .getResultList().stream().findFirst();
    }

    @Override
    public List<Project> findProjectsByUserId(String userId) {
        return entityManager.createQuery("SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.expenses WHERE p.user.id = :id", Project.class)
                .setParameter("id", userId)
                .getResultList();
    }
}
