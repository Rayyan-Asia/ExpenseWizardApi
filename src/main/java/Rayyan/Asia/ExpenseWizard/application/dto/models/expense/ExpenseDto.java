package Rayyan.Asia.ExpenseWizard.application.dto.models.expense;

import Rayyan.Asia.ExpenseWizard.domain.models.ExpenseCategory;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
public class ExpenseDto {

    private String id;

    @NotNull
    private Float cost;

    private Timestamp timestamp = Timestamp.from(Instant.now());

    @NotNull
    private ExpenseCategory expenseCategory;
    private String description;
}
