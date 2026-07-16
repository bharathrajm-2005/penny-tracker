package com.expensemanager.service.impl;

import com.expensemanager.dto.ExpenseDto;
import com.expensemanager.dto.ExpenseResponse;
import com.expensemanager.entity.Category;
import com.expensemanager.entity.Expense;
import com.expensemanager.entity.User;
import com.expensemanager.exception.ResourceNotFoundException;
import com.expensemanager.mapper.EntityMapper;
import com.expensemanager.repository.CategoryRepository;
import com.expensemanager.repository.ExpenseRepository;
import com.expensemanager.service.ExpenseService;
import com.expensemanager.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseRepository expenseRepository;
    private CategoryRepository categoryRepository;
    private SecurityUtil securityUtil;
    private EntityMapper mapper;

    @Override
    public ExpenseDto createExpense(ExpenseDto expenseDto) {
        User user = securityUtil.getLoggedInUser();
        
        Category category = categoryRepository.findByIdAndUserId(expenseDto.getCategoryId(), user.getId())
                .orElseGet(() -> categoryRepository.findById(expenseDto.getCategoryId())
                        .filter(c -> c.getUser() == null)
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + expenseDto.getCategoryId())));

        Expense expense = mapper.dtoToExpense(expenseDto);
        expense.setUser(user);
        expense.setCategory(category);
        
        Expense savedExpense = expenseRepository.save(expense);
        return mapper.expenseToDto(savedExpense);
    }

    @Override
    public ExpenseResponse getAllExpenses(int pageNo, int pageSize, String sortBy, String sortDir) {
        User user = securityUtil.getLoggedInUser();

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Expense> expenses = expenseRepository.findByUserId(user.getId(), pageable);

        List<ExpenseDto> content = expenses.getContent().stream().map(mapper::expenseToDto).collect(Collectors.toList());

        ExpenseResponse expenseResponse = new ExpenseResponse();
        expenseResponse.setContent(content);
        expenseResponse.setPageNo(expenses.getNumber());
        expenseResponse.setPageSize(expenses.getSize());
        expenseResponse.setTotalElements(expenses.getTotalElements());
        expenseResponse.setTotalPages(expenses.getTotalPages());
        expenseResponse.setLast(expenses.isLast());

        return expenseResponse;
    }

    @Override
    public ExpenseDto getExpenseById(Long id) {
        User user = securityUtil.getLoggedInUser();
        Expense expense = expenseRepository.findById(id)
                .filter(e -> e.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        return mapper.expenseToDto(expense);
    }

    @Override
    public ExpenseDto updateExpense(ExpenseDto expenseDto, Long id) {
        User user = securityUtil.getLoggedInUser();
        Expense expense = expenseRepository.findById(id)
                .filter(e -> e.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

        Category category = categoryRepository.findByIdAndUserId(expenseDto.getCategoryId(), user.getId())
                .orElseGet(() -> categoryRepository.findById(expenseDto.getCategoryId())
                        .filter(c -> c.getUser() == null)
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + expenseDto.getCategoryId())));

        expense.setAmount(expenseDto.getAmount());
        expense.setDescription(expenseDto.getDescription());
        expense.setExpenseDate(expenseDto.getExpenseDate());
        expense.setPaymentMethod(expenseDto.getPaymentMethod());
        expense.setCategory(category);

        Expense updatedExpense = expenseRepository.save(expense);
        return mapper.expenseToDto(updatedExpense);
    }

    @Override
    public void deleteExpense(Long id) {
        User user = securityUtil.getLoggedInUser();
        Expense expense = expenseRepository.findById(id)
                .filter(e -> e.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        expenseRepository.delete(expense);
    }
}
