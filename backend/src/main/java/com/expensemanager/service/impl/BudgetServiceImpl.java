package com.expensemanager.service.impl;

import com.expensemanager.dto.BudgetDto;
import com.expensemanager.entity.Budget;
import com.expensemanager.entity.Category;
import com.expensemanager.entity.User;
import com.expensemanager.exception.ResourceNotFoundException;
import com.expensemanager.mapper.EntityMapper;
import com.expensemanager.repository.BudgetRepository;
import com.expensemanager.repository.CategoryRepository;
import com.expensemanager.repository.ExpenseRepository;
import com.expensemanager.service.BudgetService;
import com.expensemanager.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of BudgetService. 
 * Handles business logic surrounding budget creation and validation.
 */
@Service
@AllArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private BudgetRepository budgetRepository;
    private CategoryRepository categoryRepository;
    private ExpenseRepository expenseRepository;
    private SecurityUtil securityUtil;
    private EntityMapper mapper;

    /**
     * Creates or updates a budget for the currently logged-in user.
     * Ties the budget to an optional category and handles authorization checks.
     * 
     * @param budgetDto The budget details
     * @return The persisted budget
     */
    @Override
    public BudgetDto setBudget(BudgetDto budgetDto) {
        User user = securityUtil.getLoggedInUser();
        Budget budget = mapper.dtoToBudget(budgetDto);
        budget.setUser(user);

        if (budgetDto.getCategoryId() != null) {
            Category category = categoryRepository.findByIdAndUserId(budgetDto.getCategoryId(), user.getId())
                    .orElseGet(() -> categoryRepository.findById(budgetDto.getCategoryId())
                            .filter(c -> c.getUser() == null)
                            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + budgetDto.getCategoryId())));
            budget.setCategory(category);
        }

        Budget savedBudget = budgetRepository.save(budget);
        return populateAmountSpent(mapper.budgetToDto(savedBudget), user.getId());
    }

    @Override
    public List<BudgetDto> getBudgetsByMonthAndYear(Integer month, Integer year) {
        User user = securityUtil.getLoggedInUser();
        List<Budget> budgets = budgetRepository.findByUserIdAndMonthAndYear(user.getId(), month, year);
        
        return budgets.stream()
                .map(mapper::budgetToDto)
                .map(dto -> populateAmountSpent(dto, user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public BudgetDto getBudgetById(Long id) {
        User user = securityUtil.getLoggedInUser();
        Budget budget = budgetRepository.findById(id)
                .filter(b -> b.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));
        return populateAmountSpent(mapper.budgetToDto(budget), user.getId());
    }

    @Override
    public BudgetDto updateBudget(BudgetDto budgetDto, Long id) {
        User user = securityUtil.getLoggedInUser();
        Budget budget = budgetRepository.findById(id)
                .filter(b -> b.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));

        budget.setAmount(budgetDto.getAmount());
        budget.setMonth(budgetDto.getMonth());
        budget.setYear(budgetDto.getYear());

        if (budgetDto.getCategoryId() != null) {
            Category category = categoryRepository.findByIdAndUserId(budgetDto.getCategoryId(), user.getId())
                    .orElseGet(() -> categoryRepository.findById(budgetDto.getCategoryId())
                            .filter(c -> c.getUser() == null)
                            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + budgetDto.getCategoryId())));
            budget.setCategory(category);
        } else {
            budget.setCategory(null);
        }

        Budget updatedBudget = budgetRepository.save(budget);
        return populateAmountSpent(mapper.budgetToDto(updatedBudget), user.getId());
    }

    @Override
    public void deleteBudget(Long id) {
        User user = securityUtil.getLoggedInUser();
        Budget budget = budgetRepository.findById(id)
                .filter(b -> b.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));
        budgetRepository.delete(budget);
    }

    private BudgetDto populateAmountSpent(BudgetDto budgetDto, Long userId) {
        BigDecimal spent;
        if (budgetDto.getCategory() != null && budgetDto.getCategory().getId() != null) {
            spent = expenseRepository.getTotalExpenseByUserIdAndCategoryIdAndMonthAndYear(
                    userId, budgetDto.getCategory().getId(), budgetDto.getMonth(), budgetDto.getYear());
        } else {
            spent = expenseRepository.getTotalExpenseByUserIdAndMonthAndYear(
                    userId, budgetDto.getMonth(), budgetDto.getYear());
        }
        budgetDto.setAmountSpent(spent != null ? spent : BigDecimal.ZERO);
        return budgetDto;
    }
}
