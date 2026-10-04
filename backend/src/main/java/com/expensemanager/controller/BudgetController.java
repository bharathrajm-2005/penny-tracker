package com.expensemanager.controller;

import com.expensemanager.dto.BudgetDto;
import com.expensemanager.service.BudgetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for managing user budgets.
 * Provides endpoints to create, read, update, and delete budgets.
 */
@RestController
@RequestMapping("/api/budgets")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class BudgetController {

    private BudgetService budgetService;

    /**
     * Sets a new budget for a specific category, month, and year.
     * 
     * @param budgetDto The budget details to be saved.
     * @return The created budget data along with a 201 Created status.
     */
    @PostMapping
    public ResponseEntity<BudgetDto> setBudget(@RequestBody BudgetDto budgetDto) {
        BudgetDto created = budgetService.setBudget(budgetDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * Retrieves all budgets for the currently authenticated user for a specific month and year.
     * 
     * @param month The month to retrieve budgets for (1-12).
     * @param year The year to retrieve budgets for.
     * @return A list of budgets matching the criteria.
     */
    @GetMapping
    public ResponseEntity<List<BudgetDto>> getBudgetsByMonthAndYear(
            @RequestParam("month") Integer month,
            @RequestParam("year") Integer year) {
        return ResponseEntity.ok(budgetService.getBudgetsByMonthAndYear(month, year));
    }

    /**
     * Retrieves a specific budget by its unique identifier.
     * 
     * @param id The ID of the budget to retrieve.
     * @return The requested budget data.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BudgetDto> getBudgetById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(budgetService.getBudgetById(id));
    }

    /**
     * Updates an existing budget's details.
     * 
     * @param budgetDto The updated budget information.
     * @param id The ID of the budget to update.
     * @return The updated budget data.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BudgetDto> updateBudget(@RequestBody BudgetDto budgetDto, @PathVariable("id") Long id) {
        return ResponseEntity.ok(budgetService.updateBudget(budgetDto, id));
    }

    /**
     * Deletes a specific budget.
     * 
     * @param id The ID of the budget to delete.
     * @return A success message upon successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBudget(@PathVariable("id") Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.ok("Budget deleted successfully!.");
    }
}
