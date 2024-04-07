package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.domain.models.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository {

    Optional<Expense> findById(String Id);
    Expense save(Expense expense);
    void delete(Expense expense);
    Page<Expense> findByUserId(String userId, Pageable pageable);
    Page<Expense> findByProjectId(String projectId, Pageable pageable);
    boolean isExpenseOwnedByUser(String expenseId, String userId);
    boolean areExpensesOwnedByUser(List<String> expenseIds, String userId);
}
