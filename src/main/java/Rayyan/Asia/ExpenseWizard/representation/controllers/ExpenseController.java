package Rayyan.Asia.ExpenseWizard.representation.controllers;

import Rayyan.Asia.ExpenseWizard.application.dto.models.expense.BulkExpenseDto;
import Rayyan.Asia.ExpenseWizard.application.dto.models.expense.ExpenseDto;
import Rayyan.Asia.ExpenseWizard.application.dto.models.user.CustomUserDetails;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ExpenseService;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ProjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExpenseController {
    private final ExpenseService expenseService;
    private final ProjectService projectService;

    @GetMapping("byId")
    public ResponseEntity<ExpenseDto> get(@RequestParam @NotNull String expenseId) {
        var authentication = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var userId = authentication.getUserId();
        try {
            if (expenseService.isExpenseOwnedByUser(expenseId, userId)) {
                var expense = expenseService.findById(expenseId);
                return expense.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("bulk")
    public ResponseEntity<List<ExpenseDto>> getProject(@RequestParam @NotNull String projectId) {
        var authentication = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var userId = authentication.getUserId();
        try {
            if (projectService.isProjectOwnedByUser(projectId, userId)) {
                var expenses = expenseService.findByProjectId(projectId);
                return new ResponseEntity<>(expenses, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("bulk")
    public ResponseEntity<List<ExpenseDto>> bulkUpsert(@RequestBody @Valid BulkExpenseDto bulkDto) {
        var authentication = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var userId = authentication.getUserId();
        try {
            var ids = bulkDto.getExpense().stream().map(ExpenseDto::getId).filter(Objects::nonNull).toList();
            if (ids.isEmpty() || expenseService.areExpensesOwnedByUser(ids, userId)) {
                var expenses = expenseService.save(bulkDto.getExpense());
                return new ResponseEntity<>(expenses, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<ExpenseDto> Upsert(@RequestBody @Valid ExpenseDto expenseDto) {
        var authentication = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var userId = authentication.getUserId();
        try {
            if (expenseDto.getId() == null || expenseService.isExpenseOwnedByUser(expenseDto.getId(), userId)) {
                var expenses = expenseService.save(expenseDto);
                return new ResponseEntity<>(expenses, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping()
    public ResponseEntity delete(@RequestParam @NotNull String expenseId) {
        var authentication = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var userId = authentication.getUserId();
        try {
            if (expenseService.isExpenseOwnedByUser(expenseId, userId)) {
                expenseService.delete(expenseId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
