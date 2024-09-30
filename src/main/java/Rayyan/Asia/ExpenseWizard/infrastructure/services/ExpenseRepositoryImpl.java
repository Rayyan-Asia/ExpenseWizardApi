package Rayyan.Asia.ExpenseWizard.infrastructure.services;

import Rayyan.Asia.ExpenseWizard.domain.interfaces.ExpenseRepository;
import Rayyan.Asia.ExpenseWizard.domain.models.Expense;
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

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExpenseRepositoryImpl implements ExpenseRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Optional<Expense> findById(String id) {
        return entityManager.createQuery("FROM Expense e WHERE e.id = :id", Expense.class)
                .setParameter("id", id)
                .getResultList().stream().findFirst();
    }

    @Override
    public Expense save(Expense expense) {
        if (expense.getId() != null)
            entityManager.merge(expense);
        else
            entityManager.persist(expense);
        return expense;
    }

    @Override
    public void delete(Expense expense) {
        entityManager.createQuery("DELETE FROM Expense e WHERE e.id = :id")
                .setParameter("id", expense.getId())
                .executeUpdate();
    }

    @Override
    public Page<Expense> findByUserId(String userId, Pageable pageable) {
        TypedQuery<Expense> query = entityManager.createQuery(
                        "SELECT e " +
                                "FROM Expense e " +
                                "JOIN e.project p " +
                                "JOIN p.user u " +
                                "WHERE u.id = :userId", Expense.class)
                .setParameter("userId", userId);
                if (pageable.isPaged()){
                    query.setFirstResult((int) pageable.getOffset())
                            .setMaxResults(pageable.getPageSize());
                }


        TypedQuery<Long> countQuery = entityManager.createQuery(
                        "SELECT COUNT(e) " +
                                "FROM Expense e " +
                                "JOIN e.project p " +
                                "JOIN p.user u " +
                                "WHERE u.id = :userId", Long.class)
                .setParameter("userId", userId);

        long totalCount = countQuery.getSingleResult();
        return new PageImpl<>(query.getResultList(), pageable, totalCount);
    }

    @Override
    public Page<Expense> findByProjectId(String projectId, Pageable pageable) {
        TypedQuery<Expense> query = entityManager.createQuery(
                        "SELECT e " +
                                "FROM Expense e " +
                                "JOIN e.project p " +
                                "WHERE p.id = :projectId", Expense.class)
                .setParameter("projectId", projectId)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(
                        "SELECT COUNT(e) " +
                                "FROM Expense e " +
                                "JOIN e.project p " +
                                "WHERE p.id = :projectId", Long.class)
                .setParameter("projectId", projectId);

        long totalCount = countQuery.getSingleResult();
        return new PageImpl<>(query.getResultList(), pageable, totalCount);
    }

    public boolean isExpenseOwnedByUser(String expenseId, String userId) {
        String queryString = "SELECT COUNT(e) " +
                "FROM Expense e " +
                "JOIN e.project p " +
                "JOIN p.user u " +
                "WHERE u.id = :userId AND e.id = :expenseId";

        Long count = entityManager.createQuery(queryString, Long.class)
                .setParameter("userId", userId)
                .setParameter("expenseId", expenseId)
                .getSingleResult();

        return count == 1;
    }


    public boolean areExpensesOwnedByUser(List<String> expenseIds, String userId) {
        if (expenseIds.isEmpty()) {
            return false;
        }

        String queryString = "SELECT COUNT(e) " +
                "FROM Expense e " +
                "JOIN e.project p " +
                "JOIN p.user u " +
                "WHERE u.id = :userId AND e.id IN :expenseIds";

        Long count = entityManager.createQuery(queryString, Long.class)
                .setParameter("userId", userId)
                .setParameter("expenseIds", expenseIds)
                .getSingleResult();

        return count == expenseIds.size();
    }

}
