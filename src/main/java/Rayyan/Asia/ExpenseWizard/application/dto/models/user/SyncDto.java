package Rayyan.Asia.ExpenseWizard.application.dto.models.user;

import Rayyan.Asia.ExpenseWizard.application.dto.models.expense.ExpenseDto;
import Rayyan.Asia.ExpenseWizard.application.dto.models.project.ProjectDto;
import lombok.Data;

import java.util.List;

@Data
public class SyncDto {
    private UserDto user;
    private List<ProjectDto> projects;
    private List<ExpenseDto> expense;
}
