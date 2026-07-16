package com.expensemanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotEmpty(message = "Email should not be null or empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Password should not be null or empty")
    private String password;
}
