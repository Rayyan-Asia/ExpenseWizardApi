package Rayyan.Asia.ExpenseWizard.application.dto.models.project;

import Rayyan.Asia.ExpenseWizard.application.dto.models.expense.ExpenseDto;
import Rayyan.Asia.ExpenseWizard.application.dto.models.user.UserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class ProjectDto {
    private String id;
    @Size(min = 3, max = 100, message
            = "Name must be between 10 and 200 characters")
    @NotBlank
    private String name;
    private Float limit;
}
