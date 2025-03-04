package com.example.stationery.users.controller.dto

import jakarta.validation.constraints.NotBlank

data class LoginRequestDTO(
    @NotBlank
    val email: String?,

    @NotBlank
    val password: String?
)
