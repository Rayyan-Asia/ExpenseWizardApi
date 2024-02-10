package Rayyan.Asia.ExpenseWizard.application.dto.models.expense;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BulkExpenseDto
{
    @NotNull
    @NotBlank
    private String projectId;
    private List<ExpenseDto> expense;
}
