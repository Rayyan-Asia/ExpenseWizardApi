package Rayyan.Asia.ExpenseWizard.application.dto.models.expense;

import Rayyan.Asia.ExpenseWizard.domain.models.ExpenseCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ExpenseDto {

    private String id;

    @NotNull
    @NotEmpty
    private Float cost;

    private Timestamp timestamp;

    @NotNull
    private ExpenseCategory expenseCategory;
}
