package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.domain.models.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository {

    Optional<Expense> findById(String Id);
    Expense save(Expense expense);
    void delete(Expense expense);
    List<Expense> findByUserId(String userId);
}
