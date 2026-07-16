package com.expensemanager.controller;

import com.expensemanager.dto.CategoryDto;
import com.expensemanager.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {

    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto created = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") Long id){
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
                                                      @PathVariable("id") Long id){
        CategoryDto updated = categoryService.updateCategory(categoryDto, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully!.");
    }
}
