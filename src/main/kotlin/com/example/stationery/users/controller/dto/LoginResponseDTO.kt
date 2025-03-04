package com.example.stationery.users.controller.dto

data class LoginResponseDTO(
    val token: String,
    val user: UserResponseDTO,
)
