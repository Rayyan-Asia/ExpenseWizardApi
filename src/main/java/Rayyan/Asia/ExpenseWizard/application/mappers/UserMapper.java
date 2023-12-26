package Rayyan.Asia.ExpenseWizard.application.mappers;

import Rayyan.Asia.ExpenseWizard.application.dto.users.UserDto;
import Rayyan.Asia.ExpenseWizard.domain.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto domainToDto(User user);
    User dtoToDomain(UserDto dto);
}
