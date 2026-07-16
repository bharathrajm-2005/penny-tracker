package com.expensemanager.mapper;

import com.expensemanager.dto.BudgetDto;
import com.expensemanager.dto.CategoryDto;
import com.expensemanager.dto.ExpenseDto;
import com.expensemanager.entity.Budget;
import com.expensemanager.entity.Category;
import com.expensemanager.entity.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    CategoryDto categoryToDto(Category category);
    Category dtoToCategory(CategoryDto dto);

    @Mapping(source = "category.id", target = "categoryId")
    ExpenseDto expenseToDto(Expense expense);
    @Mapping(source = "categoryId", target = "category.id")
    Expense dtoToExpense(ExpenseDto dto);

    @Mapping(source = "category.id", target = "categoryId")
    BudgetDto budgetToDto(Budget budget);
    @Mapping(source = "categoryId", target = "category.id")
    Budget dtoToBudget(BudgetDto dto);
}
