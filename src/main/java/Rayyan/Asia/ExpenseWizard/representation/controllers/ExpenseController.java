package Rayyan.Asia.ExpenseWizard.representation.controllers;

import Rayyan.Asia.ExpenseWizard.application.dto.models.expense.BulkExpenseDto;
import Rayyan.Asia.ExpenseWizard.application.dto.models.expense.ExpenseDto;
import Rayyan.Asia.ExpenseWizard.application.dto.models.user.CustomUserDetails;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ExpenseService;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ProjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExpenseController {
    private final ExpenseService expenseService;
    private final ProjectService projectService;

    @GetMapping("byId")
    public ResponseEntity<ExpenseDto> get(@RequestParam @NotNull String expenseId, @RequestParam @NotNull String projectId) {
        var authentication = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var userId = authentication.getUserId();
        if (projectService.isProjectOwnedByUser(projectId, userId)) {
            if (expenseService.isExpenseOwnedByProject(expenseId, projectId)) {
                var expense = expenseService.findById(expenseId);
                return expense.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("bulk")
    public ResponseEntity<List<ExpenseDto>> bulkUpsert(@RequestBody @Valid BulkExpenseDto bulkDto) {
        var authentication = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var userId = authentication.getUserId();
        if (projectService.isProjectOwnedByUser(bulkDto.getProjectId(), userId)) {
            var ids = bulkDto.getExpense().stream().map(ExpenseDto::getId).filter(Objects::nonNull).toList();
            if (ids.isEmpty() || expenseService.areExpensesOwnedByProject(ids, bulkDto.getProjectId())) {
                var expenses = expenseService.save(bulkDto.getExpense(), bulkDto.getProjectId());
                return new ResponseEntity<>(expenses, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping()
    public ResponseEntity<ExpenseDto> Upsert(@RequestBody @Valid ExpenseDto expenseDto, @RequestParam @NotNull @NotBlank String projectId) {
        var authentication = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var userId = authentication.getUserId();
        if (projectService.isProjectOwnedByUser(projectId, userId)) {
            if (expenseDto.getId() == null || expenseService.isExpenseOwnedByProject(expenseDto.getId(), projectId)) {
                var expenses = expenseService.save(expenseDto, projectId);
                return new ResponseEntity<>(expenses, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
