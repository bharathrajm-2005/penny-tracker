package com.expensemanager.controller;

import com.expensemanager.dto.ExpenseDto;
import com.expensemanager.dto.ExpenseResponse;
import com.expensemanager.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ExpenseController {

    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDto> createExpense(@RequestBody ExpenseDto expenseDto){
        ExpenseDto created = expenseService.createExpense(expenseDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ExpenseResponse> getAllExpenses(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "expenseDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir
    ){
        return ResponseEntity.ok(expenseService.getAllExpenses(pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable("id") Long id){
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDto> updateExpense(@RequestBody ExpenseDto expenseDto, @PathVariable("id") Long id){
        return ResponseEntity.ok(expenseService.updateExpense(expenseDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable("id") Long id){
        expenseService.deleteExpense(id);
        return ResponseEntity.ok("Expense deleted successfully!.");
    }
}
