package Rayyan.Asia.ExpenseWizard.application.dto.models;

import Rayyan.Asia.ExpenseWizard.domain.models.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectDto {

        private String id;
        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private UserEntity userEntity;
        @Size(min = 3, max = 100, message
                = "Name must be between 10 and 200 characters")
        @NotBlank
        private String name;
        private Float limit;
        private List<ExpenseDto> expenses = new ArrayList<>();
}
