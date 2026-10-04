package com.expensemanager.controller;

import com.expensemanager.dto.DashboardDto;
import com.expensemanager.service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for providing aggregated dashboard data.
 * This includes summary statistics like total expenses, budget status, etc.
 */
@RestController
@RequestMapping("/api/dashboard")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

    private DashboardService dashboardService;

    /**
     * Retrieves aggregated dashboard data for a specific month and year.
     * 
     * @param month The month to fetch data for (1-12).
     * @param year The year to fetch data for.
     * @return DashboardDto containing summarized expense and budget data.
     */
    @GetMapping
    public ResponseEntity<DashboardDto> getDashboardData(
            @RequestParam("month") Integer month,
            @RequestParam("year") Integer year) {
        return ResponseEntity.ok(dashboardService.getDashboardData(month, year));
    }
}
