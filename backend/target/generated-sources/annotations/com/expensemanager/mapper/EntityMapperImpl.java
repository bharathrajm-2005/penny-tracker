package com.expensemanager.mapper;

import com.expensemanager.dto.BudgetDto;
import com.expensemanager.dto.CategoryDto;
import com.expensemanager.dto.ExpenseDto;
import com.expensemanager.entity.Budget;
import com.expensemanager.entity.Category;
import com.expensemanager.entity.Expense;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-16T19:55:07+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Eclipse Adoptium)"
)
@Component
public class EntityMapperImpl implements EntityMapper {

    @Override
    public CategoryDto categoryToDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setId( category.getId() );
        categoryDto.setName( category.getName() );
        categoryDto.setIcon( category.getIcon() );

        return categoryDto;
    }

    @Override
    public Category dtoToCategory(CategoryDto dto) {
        if ( dto == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.id( dto.getId() );
        category.name( dto.getName() );
        category.icon( dto.getIcon() );

        return category.build();
    }

    @Override
    public ExpenseDto expenseToDto(Expense expense) {
        if ( expense == null ) {
            return null;
        }

        ExpenseDto expenseDto = new ExpenseDto();

        expenseDto.setCategoryId( expenseCategoryId( expense ) );
        expenseDto.setId( expense.getId() );
        expenseDto.setAmount( expense.getAmount() );
        expenseDto.setDescription( expense.getDescription() );
        expenseDto.setExpenseDate( expense.getExpenseDate() );
        expenseDto.setPaymentMethod( expense.getPaymentMethod() );
        expenseDto.setCategory( categoryToDto( expense.getCategory() ) );

        return expenseDto;
    }

    @Override
    public Expense dtoToExpense(ExpenseDto dto) {
        if ( dto == null ) {
            return null;
        }

        Expense.ExpenseBuilder expense = Expense.builder();

        expense.category( expenseDtoToCategory( dto ) );
        expense.id( dto.getId() );
        expense.amount( dto.getAmount() );
        expense.description( dto.getDescription() );
        expense.expenseDate( dto.getExpenseDate() );
        expense.paymentMethod( dto.getPaymentMethod() );

        return expense.build();
    }

    @Override
    public BudgetDto budgetToDto(Budget budget) {
        if ( budget == null ) {
            return null;
        }

        BudgetDto budgetDto = new BudgetDto();

        budgetDto.setCategoryId( budgetCategoryId( budget ) );
        budgetDto.setId( budget.getId() );
        budgetDto.setAmount( budget.getAmount() );
        budgetDto.setMonth( budget.getMonth() );
        budgetDto.setYear( budget.getYear() );
        budgetDto.setCategory( categoryToDto( budget.getCategory() ) );

        return budgetDto;
    }

    @Override
    public Budget dtoToBudget(BudgetDto dto) {
        if ( dto == null ) {
            return null;
        }

        Budget.BudgetBuilder budget = Budget.builder();

        budget.category( budgetDtoToCategory( dto ) );
        budget.id( dto.getId() );
        budget.amount( dto.getAmount() );
        budget.month( dto.getMonth() );
        budget.year( dto.getYear() );

        return budget.build();
    }

    private Long expenseCategoryId(Expense expense) {
        if ( expense == null ) {
            return null;
        }
        Category category = expense.getCategory();
        if ( category == null ) {
            return null;
        }
        Long id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Category expenseDtoToCategory(ExpenseDto expenseDto) {
        if ( expenseDto == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.id( expenseDto.getCategoryId() );

        return category.build();
    }

    private Long budgetCategoryId(Budget budget) {
        if ( budget == null ) {
            return null;
        }
        Category category = budget.getCategory();
        if ( category == null ) {
            return null;
        }
        Long id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Category budgetDtoToCategory(BudgetDto budgetDto) {
        if ( budgetDto == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.id( budgetDto.getCategoryId() );

        return category.build();
    }
}
