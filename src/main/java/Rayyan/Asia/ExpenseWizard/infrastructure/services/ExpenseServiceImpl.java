package Rayyan.Asia.ExpenseWizard.infrastructure.services;

import Rayyan.Asia.ExpenseWizard.application.dto.models.expense.ExpenseDto;
import Rayyan.Asia.ExpenseWizard.application.mappers.ExpenseMapper;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ExpenseRepository;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ExpenseService;
import Rayyan.Asia.ExpenseWizard.domain.models.Expense;
import Rayyan.Asia.ExpenseWizard.domain.models.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public ExpenseDto save(ExpenseDto expense, String id) {
        var expenseModel = mapper.dtoToDomain(expense);
        var project = new Project();
        project.setId(id);
        expenseModel.setProject(project);
        expenseRepository.save(expenseModel);
        return mapper.domainToDto(expenseModel);
    }

    @Override
    public List<ExpenseDto> save(List<ExpenseDto> expenses, String projectId) {
        var expensesDomain = mapper.dtoToDomain(expenses);
        for (Expense expense : expensesDomain) {
            var project = new Project();
            project.setId(projectId);
            expense.setProject(project);
            expenseRepository.save(expense);
        }
        return mapper.domainToDto(expensesDomain);
    }

}
