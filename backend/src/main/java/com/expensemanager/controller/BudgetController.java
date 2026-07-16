package com.expensemanager.controller;

import com.expensemanager.dto.BudgetDto;
import com.expensemanager.service.BudgetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class BudgetController {

    private BudgetService budgetService;

    @PostMapping
    public ResponseEntity<BudgetDto> setBudget(@RequestBody BudgetDto budgetDto) {
        BudgetDto created = budgetService.setBudget(budgetDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BudgetDto>> getBudgetsByMonthAndYear(
            @RequestParam("month") Integer month,
            @RequestParam("year") Integer year) {
        return ResponseEntity.ok(budgetService.getBudgetsByMonthAndYear(month, year));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetDto> getBudgetById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(budgetService.getBudgetById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetDto> updateBudget(@RequestBody BudgetDto budgetDto, @PathVariable("id") Long id) {
        return ResponseEntity.ok(budgetService.updateBudget(budgetDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBudget(@PathVariable("id") Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.ok("Budget deleted successfully!.");
    }
}
