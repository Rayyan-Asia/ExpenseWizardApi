package Rayyan.Asia.ExpenseWizard.application.mappers;

import Rayyan.Asia.ExpenseWizard.application.dto.auth.RegisterDto;
import Rayyan.Asia.ExpenseWizard.domain.models.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    RegisterDto domainToDto(UserEntity userEntity);
    UserEntity dtoToDomain(RegisterDto dto);
}
