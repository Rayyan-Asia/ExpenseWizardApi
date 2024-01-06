package Rayyan.Asia.ExpenseWizard.application.dto.models;

import Rayyan.Asia.ExpenseWizard.domain.models.ExpenseCategory;
import Rayyan.Asia.ExpenseWizard.domain.models.Project;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public class ExpenseDto {

    private String id;

    @NotNull
    private Project project;

    @NotNull
    @NotEmpty
    private Float cost;

    private Timestamp timestamp;

    @NotNull
    private ExpenseCategory expenseCategory;
}
