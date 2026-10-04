package com.expensemanager.repository;

import com.expensemanager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for CRUD operations on Category entities.
 * Supports retrieving both global default categories and user-specific custom categories.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserIdOrUserIsNull(Long userId);
    Optional<Category> findByIdAndUserId(Long id, Long userId);
}
