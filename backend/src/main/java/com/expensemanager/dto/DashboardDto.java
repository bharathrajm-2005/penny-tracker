package com.expensemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDto {
    private BigDecimal totalExpenses;
    private BigDecimal currentMonthExpenses;
    private BigDecimal totalBudget;
    private BigDecimal remainingBudget;
    private CategoryDto highestSpendingCategory;
    private List<ExpenseDto> recentTransactions;
    private Map<String, BigDecimal> categoryWiseExpenses;
}
