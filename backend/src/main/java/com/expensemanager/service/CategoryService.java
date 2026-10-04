package com.expensemanager.service;

import com.expensemanager.dto.CategoryDto;

import java.util.List;

/**
 * Service interface defining business logic for category management.
 */
public interface CategoryService {

    /**
     * Creates a new custom expense category for a user.
     * 
     * @param categoryDto The details of the category to create.
     * @return The created category data.
     */
    CategoryDto createCategory(CategoryDto categoryDto);

    /**
     * Retrieves a category by its ID.
     * 
     * @param id The category ID.
     * @return The requested category data.
     */
    CategoryDto getCategoryById(Long id);

    /**
     * Retrieves all available categories.
     * 
     * @return A list of categories.
     */
    List<CategoryDto> getAllCategories();

    /**
     * Updates an existing category's properties.
     * 
     * @param categoryDto The updated category details.
     * @param id The ID of the category to update.
     * @return The updated category data.
     */
    CategoryDto updateCategory(CategoryDto categoryDto, Long id);

    /**
     * Deletes a category by its ID.
     * 
     * @param id The ID of the category to remove.
     */
    void deleteCategory(Long id);
}
