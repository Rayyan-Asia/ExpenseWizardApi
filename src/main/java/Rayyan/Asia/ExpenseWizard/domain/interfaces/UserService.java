package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.application.dto.models.user.UserDto;
import Rayyan.Asia.ExpenseWizard.domain.models.UserEntity;

import java.util.Optional;

public interface UserService {
    public Optional<UserDto> getByEmail(String email);

    public Optional<UserDto> getById(String id);

    public UserEntity save(UserDto userEntity);
}
