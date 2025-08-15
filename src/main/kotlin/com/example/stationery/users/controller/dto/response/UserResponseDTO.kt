package com.example.stationery.users.controller.dto.response

import com.example.stationery.users.entity.User

data class UserResponseDTO(
    val id: Long,
    val name: String,
    val email: String
) {
    constructor(user: User): this(id=user.id!!,user.name, user.email)
}
