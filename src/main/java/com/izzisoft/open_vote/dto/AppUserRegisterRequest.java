package com.izzisoft.open_vote.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AppUserRegisterRequest(
        @NotBlank(message = "Email cannot be empty!")
        @Email(message = "Email should be valid!")
        String email,
        @NotBlank(message = "Password cannot be empty!")
        @Size(min = 4, message = "Password must be at least 4 characters long!")
        String password
) {
}
