package com.expensemanager.service.impl;

import com.expensemanager.dto.CategoryDto;
import com.expensemanager.entity.Category;
import com.expensemanager.entity.User;
import com.expensemanager.exception.ResourceNotFoundException;
import com.expensemanager.mapper.EntityMapper;
import com.expensemanager.repository.CategoryRepository;
import com.expensemanager.service.CategoryService;
import com.expensemanager.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private SecurityUtil securityUtil;
    private EntityMapper mapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        User user = securityUtil.getLoggedInUser();
        Category category = mapper.dtoToCategory(categoryDto);
        category.setUser(user);
        Category savedCategory = categoryRepository.save(category);
        return mapper.categoryToDto(savedCategory);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        User user = securityUtil.getLoggedInUser();
        Category category = categoryRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return mapper.categoryToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        User user = securityUtil.getLoggedInUser();
        List<Category> categories = categoryRepository.findByUserIdOrUserIsNull(user.getId());
        return categories.stream().map(mapper::categoryToDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        User user = securityUtil.getLoggedInUser();
        Category category = categoryRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        category.setName(categoryDto.getName());
        category.setIcon(categoryDto.getIcon());
        Category updatedCategory = categoryRepository.save(category);
        return mapper.categoryToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        User user = securityUtil.getLoggedInUser();
        Category category = categoryRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        categoryRepository.delete(category);
    }
}
