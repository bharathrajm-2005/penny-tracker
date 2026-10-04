package com.expensemanager.repository;

import com.expensemanager.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for CRUD operations on Budget entities.
 * Includes custom queries for retrieving user-specific budgets and aggregating total budget amounts.
 */
@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUserIdAndMonthAndYear(Long userId, Integer month, Integer year);
    Optional<Budget> findByUserIdAndCategoryIdAndMonthAndYear(Long userId, Long categoryId, Integer month, Integer year);
    
    @org.springframework.data.jpa.repository.Query("SELECT COALESCE(SUM(b.amount), 0) FROM Budget b WHERE b.user.id = :userId AND b.month = :month AND b.year = :year")
    java.math.BigDecimal getTotalBudgetByUserIdAndMonthAndYear(@org.springframework.data.repository.query.Param("userId") Long userId, @org.springframework.data.repository.query.Param("month") Integer month, @org.springframework.data.repository.query.Param("year") Integer year);
}
