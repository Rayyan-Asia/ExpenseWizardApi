package Rayyan.Asia.ExpenseWizard.infrastructure.services;

import Rayyan.Asia.ExpenseWizard.application.dto.models.ExpenseDto;
import Rayyan.Asia.ExpenseWizard.application.mappers.ExpenseMapper;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ExpenseRepository;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseMapper mapper;
    private final ExpenseRepository expenseRepository;

    @Override
    public Optional<ExpenseDto> findById(String id) {
        var expense =  expenseRepository.findById(id);
        return expense.map(mapper::domainToDto);
    }

    @Override
    public ExpenseDto save(ExpenseDto expense) {
        var expenseModel = mapper.dtoToDomain(expense);
        expenseRepository.save(expenseModel);
        return mapper.domainToDto(expenseModel);
    }
}
