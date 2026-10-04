package com.expensemanager.controller;

import com.expensemanager.dto.JwtAuthResponse;
import com.expensemanager.dto.LoginDto;
import com.expensemanager.dto.RegisterDto;
import com.expensemanager.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling user authentication operations.
 * Exposes endpoints for user registration and login.
 */
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*") // Adjust this for production to restrict allowed origins
public class AuthController {

    private AuthService authService;

    /**
     * Authenticates a user based on their credentials and generates a JWT token.
     * 
     * @param loginDto Data Transfer Object containing user's username/email and password.
     * @return A response entity containing the generated JWT access token if successful.
     */
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginDto loginDto){
        // Delegate authentication to the auth service which returns a JWT token
        String token = authService.login(loginDto);

        // Wrap the token in a response object
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    /**
     * Registers a new user in the system.
     * 
     * @param registerDto Data Transfer Object containing new user's details.
     * @return A success message indicating the user was registered.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto){
        // Delegate registration to the auth service
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
