package com.expensemanager.controller;

import com.expensemanager.dto.DashboardDto;
import com.expensemanager.service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

    private DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardDto> getDashboardData(
            @RequestParam("month") Integer month,
            @RequestParam("year") Integer year) {
        return ResponseEntity.ok(dashboardService.getDashboardData(month, year));
    }
}
