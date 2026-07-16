package com.expensemanager.service;

import com.expensemanager.dto.BudgetDto;

import java.util.List;

public interface BudgetService {
    BudgetDto setBudget(BudgetDto budgetDto);
    List<BudgetDto> getBudgetsByMonthAndYear(Integer month, Integer year);
    BudgetDto getBudgetById(Long id);
    BudgetDto updateBudget(BudgetDto budgetDto, Long id);
    void deleteBudget(Long id);
}
