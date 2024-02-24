package Rayyan.Asia.ExpenseWizard.infrastructure.services;

import Rayyan.Asia.ExpenseWizard.domain.interfaces.ExpenseRepository;
import Rayyan.Asia.ExpenseWizard.domain.models.Expense;
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
    public List<Expense> findByUserId(String userId) {
        return entityManager.createQuery(
                        "SELECT e " +
                                "FROM Expense e " +
                                "JOIN e.project p " +
                                "JOIN p.user u " +
                                "WHERE u.id = :userId", Expense.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
