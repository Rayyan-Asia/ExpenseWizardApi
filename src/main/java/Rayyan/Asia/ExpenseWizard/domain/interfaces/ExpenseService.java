package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.domain.models.Expense;

import java.util.Optional;

public interface ExpenseService {
    Optional<Expense> findById(String id);

    Expense save(Expense expense);
}
