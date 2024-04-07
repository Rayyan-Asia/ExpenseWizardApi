package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.application.dto.models.expense.ExpenseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    Optional<ExpenseDto> findById(String id);

    Page<ExpenseDto> findByProjectId(String projectId, Pageable pageable);
    Page<ExpenseDto> findByUserId(String userId, Pageable pageable);

    ExpenseDto save(ExpenseDto expense);

    List<ExpenseDto> save(List<ExpenseDto> expenses);
    void delete(String expenseId);
    boolean areExpensesOwnedByUser(List<String> expenseIds, String userId);
    boolean isExpenseOwnedByUser(String expenseId, String userId);
}
