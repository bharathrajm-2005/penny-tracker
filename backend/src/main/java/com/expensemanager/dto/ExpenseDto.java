package com.expensemanager.dto;

import com.expensemanager.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto {
    private Long id;
    private BigDecimal amount;
    private String description;
    private LocalDate expenseDate;
    private PaymentMethod paymentMethod;
    private CategoryDto category;
    private Long categoryId; // Used for creation
}
