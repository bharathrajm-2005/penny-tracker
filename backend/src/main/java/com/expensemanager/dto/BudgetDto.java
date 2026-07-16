package com.expensemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDto {
    private Long id;
    private BigDecimal amount;
    private Integer month;
    private Integer year;
    private CategoryDto category;
    private Long categoryId;
    private BigDecimal amountSpent;
}
