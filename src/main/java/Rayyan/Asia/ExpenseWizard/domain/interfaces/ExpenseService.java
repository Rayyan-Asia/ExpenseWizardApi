package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.application.dto.models.expense.ExpenseDto;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    Optional<ExpenseDto> findById(String id);

    ExpenseDto save(ExpenseDto expense, String projectId);

    List<ExpenseDto> save(List<ExpenseDto> expenses, String projectId);
    boolean areExpensesOwnedByProject(List<String> expenseIds, String projectId);
    boolean isExpenseOwnedByProject(String expenseId, String projectId);
}
