package com.expensemanager.service;

import com.expensemanager.dto.BudgetDto;

import java.util.List;

/**
 * Service interface for defining business logic related to budget management.
 */
public interface BudgetService {
    
    /**
     * Creates or overwrites a budget for a category in a specific month and year.
     * 
     * @param budgetDto The budget information to save.
     * @return The persisted budget data.
     */
    BudgetDto setBudget(BudgetDto budgetDto);

    /**
     * Retrieves all budgets configured by the user for a given month and year.
     * Includes aggregated data like amount spent against the budget.
     * 
     * @param month The month to filter by (1-12).
     * @param year The year to filter by.
     * @return A list of budgets for the specified period.
     */
    List<BudgetDto> getBudgetsByMonthAndYear(Integer month, Integer year);

    /**
     * Retrieves a single budget by its ID.
     * 
     * @param id The unique identifier of the budget.
     * @return The budget data.
     */
    BudgetDto getBudgetById(Long id);

    /**
     * Updates an existing budget record.
     * 
     * @param budgetDto The updated budget data.
     * @param id The ID of the budget to update.
     * @return The updated budget.
     */
    BudgetDto updateBudget(BudgetDto budgetDto, Long id);

    /**
     * Deletes a budget by its ID.
     * 
     * @param id The ID of the budget to remove.
     */
    void deleteBudget(Long id);
}
