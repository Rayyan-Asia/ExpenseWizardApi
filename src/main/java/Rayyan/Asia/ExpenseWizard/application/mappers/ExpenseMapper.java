package Rayyan.Asia.ExpenseWizard.application.mappers;

import Rayyan.Asia.ExpenseWizard.application.dto.models.expense.ExpenseDto;
import Rayyan.Asia.ExpenseWizard.domain.models.Expense;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    ExpenseDto domainToDto(Expense expense);
    Expense dtoToDomain(ExpenseDto dto);
    List<ExpenseDto> domainToDto(List<Expense> expense);
    List<Expense> dtoToDomain(List<ExpenseDto> dto);
}