package Rayyan.Asia.ExpenseWizard.application.dto.models.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private String id;

    @Size(min = 3, max = 255, message
            = "Name must be between 3 and 255 characters")
    @NotBlank
    private String name;

    @Size(min = 3, max = 255, message
            = "Email must be between 3 and 255 characters")
    @Email
    @NotBlank
    private String email;

    @NotNull( message = "Birth Day is needed")
    private Date birthDate;
}
