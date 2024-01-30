package Rayyan.Asia.ExpenseWizard.application.messages.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDto {
    @Size(min = 3, max = 255, message
            = "Email must be between 3 and 255 characters")
    @Email
    @NotBlank
    public String email;

    @Size(min = 3, max = 255, message
            = "Password must be between 3 and 255 characters")
    @NotBlank
    public String password;
}
