package com.expensemanager.service;

import com.expensemanager.dto.ExpenseDto;
import com.expensemanager.dto.ExpenseResponse;

/**
 * Service interface defining business operations for expense tracking.
 */
public interface ExpenseService {

    /**
     * Logs a new expense record for the user.
     * 
     * @param expenseDto Details of the expense.
     * @return The persisted expense data.
     */
    ExpenseDto createExpense(ExpenseDto expenseDto);

    /**
     * Fetches a paginated and sorted list of all expenses for the user.
     * 
     * @param pageNo The page number (zero-based).
     * @param pageSize Number of records per page.
     * @param sortBy Field to sort by.
     * @param sortDir Sort direction (asc/desc).
     * @return A paginated response object containing expenses.
     */
    ExpenseResponse getAllExpenses(int pageNo, int pageSize, String sortBy, String sortDir);

    /**
     * Retrieves an expense by its ID.
     * 
     * @param id The expense ID.
     * @return The expense data.
     */
    ExpenseDto getExpenseById(Long id);

    /**
     * Updates a specific expense record.
     * 
     * @param expenseDto The updated expense details.
     * @param id The ID of the expense to update.
     * @return The updated expense data.
     */
    ExpenseDto updateExpense(ExpenseDto expenseDto, Long id);

    /**
     * Removes an expense by its ID.
     * 
     * @param id The ID of the expense to delete.
     */
    void deleteExpense(Long id);
}
