package com.expensemanager.service;

import com.expensemanager.dto.LoginDto;
import com.expensemanager.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
