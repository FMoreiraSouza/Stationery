package com.example.stationery.users.controller.dto.response

data class LoginResponseDTO(
    val token: String,
    val user: UserResponseDTO,
)
