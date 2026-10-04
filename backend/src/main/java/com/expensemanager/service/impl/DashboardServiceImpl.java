package com.expensemanager.service.impl;

import com.expensemanager.dto.CategoryDto;
import com.expensemanager.dto.DashboardDto;
import com.expensemanager.dto.ExpenseDto;
import com.expensemanager.entity.Expense;
import com.expensemanager.entity.User;
import com.expensemanager.mapper.EntityMapper;
import com.expensemanager.repository.BudgetRepository;
import com.expensemanager.repository.CategoryRepository;
import com.expensemanager.repository.ExpenseRepository;
import com.expensemanager.service.DashboardService;
import com.expensemanager.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of DashboardService.
 * Handles the calculation of aggregate metrics like total expenses, remaining budget, 
 * category-wise spending, and fetches recent transactions.
 */
@Service
@AllArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private ExpenseRepository expenseRepository;
    private BudgetRepository budgetRepository;
    private CategoryRepository categoryRepository;
    private SecurityUtil securityUtil;
    private EntityMapper mapper;

    /**
     * Gathers all the data required to populate the user's dashboard for a specific month and year.
     * Calculates total expenses, budget usage, and breaks down spending by category.
     * 
     * @param month The month to get data for.
     * @param year The year to get data for.
     * @return DashboardDto populated with various metrics and recent transactions.
     */
    @Override
    public DashboardDto getDashboardData(Integer month, Integer year) {
        User user = securityUtil.getLoggedInUser();
        Long userId = user.getId();

        DashboardDto dashboardDto = new DashboardDto();

        // Total expenses all time
        dashboardDto.setTotalExpenses(expenseRepository.getTotalExpenseByUserId(userId));

        // Current month expenses
        dashboardDto.setCurrentMonthExpenses(expenseRepository.getTotalExpenseByUserIdAndMonthAndYear(userId, month, year));

        // Total Budget
        BigDecimal totalBudget = budgetRepository.getTotalBudgetByUserIdAndMonthAndYear(userId, month, year);
        dashboardDto.setTotalBudget(totalBudget);

        // Remaining Budget
        BigDecimal currentMonthExpenses = dashboardDto.getCurrentMonthExpenses();
        if (totalBudget != null && currentMonthExpenses != null) {
            dashboardDto.setRemainingBudget(totalBudget.subtract(currentMonthExpenses));
        } else {
            dashboardDto.setRemainingBudget(BigDecimal.ZERO);
        }

        // Category wise expenses
        List<Object[]> categoryData = expenseRepository.getCategoryWiseExpenses(userId, month, year);
        Map<String, BigDecimal> categoryWiseExpenses = new HashMap<>();
        String highestCategoryName = null;
        BigDecimal highestCategoryAmount = BigDecimal.ZERO;

        for (Object[] row : categoryData) {
            String catName = (String) row[0];
            BigDecimal amount = (BigDecimal) row[1];
            categoryWiseExpenses.put(catName, amount);
            
            if (amount.compareTo(highestCategoryAmount) > 0) {
                highestCategoryAmount = amount;
                highestCategoryName = catName;
            }
        }
        dashboardDto.setCategoryWiseExpenses(categoryWiseExpenses);

        // Highest spending category
        if (highestCategoryName != null) {
            CategoryDto highestCat = new CategoryDto();
            highestCat.setName(highestCategoryName);
            dashboardDto.setHighestSpendingCategory(highestCat);
        }

        // Recent transactions (Top 5)
        Page<Expense> recentExpenses = expenseRepository.findByUserId(
                userId, PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "expenseDate", "createdAt")));
        
        List<ExpenseDto> recentTransactions = recentExpenses.getContent().stream()
                .map(mapper::expenseToDto)
                .collect(Collectors.toList());
        dashboardDto.setRecentTransactions(recentTransactions);

        return dashboardDto;
    }
}
