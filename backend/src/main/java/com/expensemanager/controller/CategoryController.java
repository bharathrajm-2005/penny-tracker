package com.expensemanager.controller;

import com.expensemanager.dto.CategoryDto;
import com.expensemanager.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for managing expense categories.
 * Provides endpoints to perform CRUD operations on categories.
 */
@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {

    private CategoryService categoryService;

    /**
     * Creates a new custom expense category.
     * 
     * @param categoryDto The details of the new category.
     * @return The created category with a 201 Created status.
     */
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto created = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * Retrieves all available categories.
     * This may include both global default categories and user-specific custom categories.
     * 
     * @return A list of all categories.
     */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * Retrieves a specific category by its ID.
     * 
     * @param id The ID of the requested category.
     * @return The requested category data.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") Long id){
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryDto);
    }

    /**
     * Updates an existing category.
     * 
     * @param categoryDto The updated category details.
     * @param id The ID of the category to update.
     * @return The updated category data.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
                                                      @PathVariable("id") Long id){
        CategoryDto updated = categoryService.updateCategory(categoryDto, id);
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes a specific category.
     * 
     * @param id The ID of the category to delete.
     * @return A success message upon successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully!.");
    }
}
