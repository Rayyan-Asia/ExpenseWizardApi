package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.domain.models.User;

import java.util.Optional;

public interface UserService {
    public Optional<User> getByEmail(String email);
    public Optional<User> getById(String id);
    public User save(User user);
}
