package Rayyan.Asia.ExpenseWizard.application.mappers;

import Rayyan.Asia.ExpenseWizard.application.dto.models.user.UserDto;
import Rayyan.Asia.ExpenseWizard.domain.models.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto domainToDto(UserEntity userEntity);
    UserEntity dtoToDomain(UserDto dto);
}
