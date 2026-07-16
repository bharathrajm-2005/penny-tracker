package com.expensemanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @NotEmpty(message = "Email should not be null or empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Password should not be null or empty")
    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;

    private String currency = "USD";
}
