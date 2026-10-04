package com.expensemanager.repository;

import com.expensemanager.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for CRUD operations on Expense entities.
 * Provides custom queries for paginated retrieval, date-range filtering, and aggregating expense totals by category/time.
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {
    Page<Expense> findByUserId(Long userId, Pageable pageable);
    
    // For calculating total expense in a date range (e.g. current month)
    List<Expense> findByUserIdAndExpenseDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
    
    @org.springframework.data.jpa.repository.Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user.id = :userId AND EXTRACT(MONTH FROM e.expenseDate) = :month AND EXTRACT(YEAR FROM e.expenseDate) = :year")
    java.math.BigDecimal getTotalExpenseByUserIdAndMonthAndYear(@org.springframework.data.repository.query.Param("userId") Long userId, @org.springframework.data.repository.query.Param("month") Integer month, @org.springframework.data.repository.query.Param("year") Integer year);

    @org.springframework.data.jpa.repository.Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user.id = :userId AND e.category.id = :categoryId AND EXTRACT(MONTH FROM e.expenseDate) = :month AND EXTRACT(YEAR FROM e.expenseDate) = :year")
    java.math.BigDecimal getTotalExpenseByUserIdAndCategoryIdAndMonthAndYear(@org.springframework.data.repository.query.Param("userId") Long userId, @org.springframework.data.repository.query.Param("categoryId") Long categoryId, @org.springframework.data.repository.query.Param("month") Integer month, @org.springframework.data.repository.query.Param("year") Integer year);
    @org.springframework.data.jpa.repository.Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user.id = :userId")
    java.math.BigDecimal getTotalExpenseByUserId(@org.springframework.data.repository.query.Param("userId") Long userId);

    @org.springframework.data.jpa.repository.Query("SELECT e.category.name, SUM(e.amount) FROM Expense e WHERE e.user.id = :userId AND EXTRACT(MONTH FROM e.expenseDate) = :month AND EXTRACT(YEAR FROM e.expenseDate) = :year GROUP BY e.category.name")
    List<Object[]> getCategoryWiseExpenses(@org.springframework.data.repository.query.Param("userId") Long userId, @org.springframework.data.repository.query.Param("month") Integer month, @org.springframework.data.repository.query.Param("year") Integer year);
}
