package Rayyan.Asia.ExpenseWizard.application.dto.models.project;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProjectListDto {
    private String id;
    private String name;
    private Float limit;
    private Float expensesThisMonth;
}
