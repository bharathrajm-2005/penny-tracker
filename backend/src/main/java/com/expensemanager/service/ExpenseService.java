package com.expensemanager.service;

import com.expensemanager.dto.ExpenseDto;
import com.expensemanager.dto.ExpenseResponse;

public interface ExpenseService {
    ExpenseDto createExpense(ExpenseDto expenseDto);
    ExpenseResponse getAllExpenses(int pageNo, int pageSize, String sortBy, String sortDir);
    ExpenseDto getExpenseById(Long id);
    ExpenseDto updateExpense(ExpenseDto expenseDto, Long id);
    void deleteExpense(Long id);
}
