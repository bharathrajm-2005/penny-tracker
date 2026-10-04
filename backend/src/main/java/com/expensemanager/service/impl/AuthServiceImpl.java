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

/**
 * Implementation of AuthService that handles user authentication and registration using Spring Security and JWT.
 */
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Authenticates a user's credentials against the database and issues a JWT token.
     * 
     * @param loginDto Contains email and password.
     * @return The generated JWT token string.
     */
    @Override
    public String login(LoginDto loginDto) {
        // Use Spring Security's authentication manager to authenticate credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        // Store authentication object in the security context for the duration of the request
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate and return JWT token upon successful authentication
        return jwtTokenProvider.generateToken(authentication);
    }

    /**
     * Registers a new user, hashes their password, and saves them to the database.
     * 
     * @param registerDto Contains name, email, password, and optional preferred currency.
     * @return A success message.
     * @throws BadRequestException if the provided email is already registered.
     */
    @Override
    public String register(RegisterDto registerDto) {

        // Check if user exists to prevent duplicates
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new BadRequestException("Email is already exists!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        // Always hash passwords before storing them in the database!
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        
        // Handle optional currency setting
        if (registerDto.getCurrency() != null && !registerDto.getCurrency().isEmpty()) {
            user.setCurrency(registerDto.getCurrency());
        }

        userRepository.save(user);

        return "User registered successfully!";
    }
}
