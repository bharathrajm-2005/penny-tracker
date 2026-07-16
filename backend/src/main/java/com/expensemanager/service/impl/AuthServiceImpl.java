package com.expensemanager.service.impl;

import com.expensemanager.dto.LoginDto;
import com.expensemanager.dto.RegisterDto;
import com.expensemanager.entity.User;
import com.expensemanager.exception.BadRequestException;
import com.expensemanager.repository.UserRepository;
import com.expensemanager.security.JwtTokenProvider;
import com.expensemanager.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String register(RegisterDto registerDto) {

        // Check if user exists
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new BadRequestException("Email is already exists!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        
        if (registerDto.getCurrency() != null && !registerDto.getCurrency().isEmpty()) {
            user.setCurrency(registerDto.getCurrency());
        }

        userRepository.save(user);

        return "User registered successfully!";
    }
}
