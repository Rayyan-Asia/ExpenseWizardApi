package Rayyan.Asia.ExpenseWizard.application.mappers;

import Rayyan.Asia.ExpenseWizard.application.dto.models.expense.ExpenseDto;
import Rayyan.Asia.ExpenseWizard.domain.models.Expense;
import org.mapstruct.Mapper;

import java.util.List;

import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mapping(target = "projectId", source = "project.id")
    ExpenseDto domainToDto(Expense expense);

    @Mapping(target = "project.id", source = "projectId")
    Expense dtoToDomain(ExpenseDto dto);

    List<ExpenseDto> domainToDto(List<Expense> expense);

    List<Expense> dtoToDomain(List<ExpenseDto> dto);
}
