package com.expensemanager.service;

import com.expensemanager.dto.DashboardDto;

/**
 * Service interface for gathering summary information for the dashboard.
 */
public interface DashboardService {
    
    /**
     * Calculates and aggregates dashboard data for a given month and year.
     * 
     * @param month The month to aggregate data for (1-12).
     * @param year The year to aggregate data for.
     * @return DashboardDto containing aggregated totals (income, expenses, budget status).
     */
    DashboardDto getDashboardData(Integer month, Integer year);
}
