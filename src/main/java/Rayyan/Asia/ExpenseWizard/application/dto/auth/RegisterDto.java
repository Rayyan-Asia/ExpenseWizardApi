package Rayyan.Asia.ExpenseWizard.application.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class RegisterDto {

    @Size(min = 3, max = 255, message
            = "Name must be between 3 and 255 characters")
    @NotBlank
    public String name;

    @Size(min = 3, max = 255, message
            = "Email must be between 3 and 255 characters")
    @Email
    @NotBlank
    public String email;

    @Size(min = 3, max = 255, message
            = "Password must be between 3 and 255 characters")
    @NotBlank
    public String password;

    @NotNull( message = "Birth Day is needed")
    public Date birthDate;
}
