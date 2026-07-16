package com.expensemanager.service;

import com.expensemanager.dto.DashboardDto;

public interface DashboardService {
    DashboardDto getDashboardData(Integer month, Integer year);
}
