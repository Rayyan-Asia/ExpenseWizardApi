package Rayyan.Asia.ExpenseWizard.infrastructure.services;

import Rayyan.Asia.ExpenseWizard.domain.interfaces.ProjectRepository;
import Rayyan.Asia.ExpenseWizard.domain.models.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
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
    public Page<Project> findProjectsByUserId(String userId, Pageable pageable) {
        TypedQuery<Project> query = entityManager.createQuery(
                        "SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.expenses WHERE p.user.id = :id", Project.class)
                .setParameter("id", userId);

        // Set pagination parameters
        if (pageable.isPaged()){
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }

        List<Project> resultList = query.getResultList();

        // Count total number of results without pagination
        long totalCount = entityManager.createQuery(
                        "SELECT COUNT(DISTINCT p) FROM Project p WHERE p.user.id = :id", Long.class)
                .setParameter("id", userId)
                .getSingleResult();

        return new PageImpl<>(resultList, pageable, totalCount);
    }

    @Override
    public Page<Project> findProjectsWithCurrentMonthExpenses(String userId, Pageable pageable) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Timestamp startOfMonth = new Timestamp(calendar.getTimeInMillis());

        TypedQuery<Project> query = entityManager.createQuery(
                        "SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.expenses e WHERE p.user.id = :id AND e.timestamp >= :startOfMonth", Project.class)
                .setParameter("id", userId)
                .setParameter("startOfMonth", startOfMonth);

        // Set pagination parameters
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Project> resultList = query.getResultList();

        // Count total number of results without pagination
        long totalCount = entityManager.createQuery(
                        "SELECT COUNT(DISTINCT p) FROM Project p LEFT JOIN p.expenses e WHERE p.user.id = :id AND e.timestamp >= :startOfMonth", Long.class)
                .setParameter("id", userId)
                .setParameter("startOfMonth", startOfMonth)
                .getSingleResult();

        return new PageImpl<>(resultList, pageable, totalCount);
    }
}
