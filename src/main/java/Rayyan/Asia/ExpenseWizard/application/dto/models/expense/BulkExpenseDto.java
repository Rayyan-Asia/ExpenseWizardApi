package Rayyan.Asia.ExpenseWizard.application.dto.models.expense;

import lombok.Data;

import java.util.List;

@Data
public class BulkExpenseDto
{
    private List<ExpenseDto> expense;
}
