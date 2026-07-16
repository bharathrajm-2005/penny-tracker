package com.expensemanager.service;

import com.expensemanager.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto getCategoryById(Long id);
    List<CategoryDto> getAllCategories();
    CategoryDto updateCategory(CategoryDto categoryDto, Long id);
    void deleteCategory(Long id);
}
