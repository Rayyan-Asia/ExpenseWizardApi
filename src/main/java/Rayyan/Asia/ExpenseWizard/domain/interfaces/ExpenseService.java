package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.application.dto.models.expense.ExpenseDto;

import java.util.Optional;

public interface ExpenseService {
    Optional<ExpenseDto> findById(String id);

    ExpenseDto save(ExpenseDto expense);
}
