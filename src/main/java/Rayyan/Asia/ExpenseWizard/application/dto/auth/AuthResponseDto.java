package Rayyan.Asia.ExpenseWizard.application.dto.auth;

import lombok.Data;

@Data
public class AuthResponseDto {
    public AuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    private String accessToken;
private String tokenType = "Bearer ";
}
