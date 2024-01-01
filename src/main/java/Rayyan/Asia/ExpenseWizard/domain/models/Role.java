package Rayyan.Asia.ExpenseWizard.domain.models;

public enum Role {
    USER("ROLE"),
    ADMIN("ROLE");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
