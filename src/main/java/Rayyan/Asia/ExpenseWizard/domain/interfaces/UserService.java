package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.domain.models.UserEntity;

import java.util.Optional;

public interface UserService {
    public Optional<UserEntity> getByEmail(String email);
    public Optional<UserEntity> getById(String id);
    public UserEntity save(UserEntity userEntity);
}
