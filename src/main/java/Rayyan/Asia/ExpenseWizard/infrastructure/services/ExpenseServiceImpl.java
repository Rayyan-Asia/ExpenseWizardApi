package Rayyan.Asia.ExpenseWizard.infrastructure.services;

import Rayyan.Asia.ExpenseWizard.application.dto.models.expense.ExpenseDto;
import Rayyan.Asia.ExpenseWizard.application.mappers.ExpenseMapper;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ExpenseRepository;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ExpenseService;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ProjectRepository;
import Rayyan.Asia.ExpenseWizard.domain.models.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseMapper mapper;
    private final ExpenseRepository expenseRepository;

    @Override
    @Transactional
    public Optional<ExpenseDto> findById(String id) {
        var expense = expenseRepository.findById(id);
        return expense.map(mapper::domainToDto);
    }

    @Override
    @Transactional
    public Page<ExpenseDto> findByProjectId(String projectId, Pageable pageable) {
        return expenseRepository.findByProjectId(projectId, pageable).map(mapper::domainToDto);
    }

    @Override
    public Page<ExpenseDto> findByUserId(String userId, Pageable pageable) {
        return expenseRepository.findByUserId(userId, pageable).map(mapper::domainToDto);
    }

    @Override
    @Transactional
    public ExpenseDto save(ExpenseDto expense) {
        var expenseModel = mapper.dtoToDomain(expense);
        var project = new Project();
        project.setId(expense.getProjectId());
        expenseModel.setProject(project);
        expenseRepository.save(expenseModel);
        return mapper.domainToDto(expenseModel);
    }

    @Override
    @Transactional
    public List<ExpenseDto> save(List<ExpenseDto> expenses) {
        var dtos = new  ArrayList<ExpenseDto>();
        for (ExpenseDto expense : expenses) {
            var dto = save(expense);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    @Transactional
    public void delete(String expenseId) {
        var expense = expenseRepository.findById(expenseId);
        expense.ifPresent(expenseRepository::delete);
    }

    @Override
    @Transactional
    public boolean areExpensesOwnedByUser(List<String> expenseIds, String userId) {
         return expenseRepository.areExpensesOwnedByUser(expenseIds, userId);
    }

    @Override
    @Transactional
    public boolean isExpenseOwnedByUser(String expenseId, String userId) {
        return expenseRepository.isExpenseOwnedByUser(expenseId, userId);
    }
}
