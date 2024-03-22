package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.application.dto.models.expense.ExpenseDto;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    Optional<ExpenseDto> findById(String id);

    List<ExpenseDto> findByProjectId(String projectId);
    List<ExpenseDto> findByUserId(String userId);

    ExpenseDto save(ExpenseDto expense);

    List<ExpenseDto> save(List<ExpenseDto> expenses);
    void delete(String expenseId);
    boolean areExpensesOwnedByUser(List<String> expenseIds, String userId);
    boolean isExpenseOwnedByUser(String expenseId, String userId);
}
