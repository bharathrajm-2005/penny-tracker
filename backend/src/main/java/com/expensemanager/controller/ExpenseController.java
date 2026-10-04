package com.expensemanager.controller;

import com.expensemanager.dto.ExpenseDto;
import com.expensemanager.dto.ExpenseResponse;
import com.expensemanager.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for managing user expenses.
 * Provides endpoints for CRUD operations and paginated retrieval of expenses.
 */
@RestController
@RequestMapping("/api/expenses")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ExpenseController {

    private ExpenseService expenseService;

    /**
     * Records a new expense for the authenticated user.
     * 
     * @param expenseDto Details of the expense to be created.
     * @return The created expense with a 201 Created status.
     */
    @PostMapping
    public ResponseEntity<ExpenseDto> createExpense(@RequestBody ExpenseDto expenseDto){
        ExpenseDto created = expenseService.createExpense(expenseDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * Retrieves a paginated and sorted list of all expenses for the authenticated user.
     * 
     * @param pageNo The page number to retrieve (defaults to 0).
     * @param pageSize The number of records per page (defaults to 10).
     * @param sortBy The field to sort the results by (defaults to expenseDate).
     * @param sortDir The direction of sorting, either 'asc' or 'desc' (defaults to desc).
     * @return A paginated response containing a list of expenses and pagination metadata.
     */
    @GetMapping
    public ResponseEntity<ExpenseResponse> getAllExpenses(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "expenseDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir
    ){
        return ResponseEntity.ok(expenseService.getAllExpenses(pageNo, pageSize, sortBy, sortDir));
    }

    /**
     * Retrieves a specific expense by its ID.
     * 
     * @param id The ID of the expense.
     * @return The requested expense details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable("id") Long id){
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    /**
     * Updates an existing expense.
     * 
     * @param expenseDto The updated expense details.
     * @param id The ID of the expense to update.
     * @return The updated expense data.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDto> updateExpense(@RequestBody ExpenseDto expenseDto, @PathVariable("id") Long id){
        return ResponseEntity.ok(expenseService.updateExpense(expenseDto, id));
    }

    /**
     * Deletes a specific expense.
     * 
     * @param id The ID of the expense to delete.
     * @return A success message upon successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable("id") Long id){
        expenseService.deleteExpense(id);
        return ResponseEntity.ok("Expense deleted successfully!.");
    }
}
