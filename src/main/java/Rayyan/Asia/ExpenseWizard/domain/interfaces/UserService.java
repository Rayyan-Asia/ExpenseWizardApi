package Rayyan.Asia.ExpenseWizard.domain.interfaces;

import Rayyan.Asia.ExpenseWizard.application.dto.models.user.UserDto;
import Rayyan.Asia.ExpenseWizard.application.dto.models.user.UserUpsertDto;

import java.util.Optional;

public interface UserService {
    public Optional<UserDto> getByEmail(String email);

    public Optional<UserDto> getById(String id);

    public UserDto save(UserUpsertDto userEntity);
}
