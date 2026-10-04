package com.expensemanager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Represents a budget configured by a user for a specific month and year.
 * A budget can either be overall for the month (category is null) 
 * or constrained to a specific expense category.
 */
@Entity
@Table(name = "budgets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "budget_month", nullable = false)
    private Integer month;

    @Column(name = "budget_year", nullable = false)
    private Integer year;

    // Optional category for category-specific budget, null means overall monthly budget
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
