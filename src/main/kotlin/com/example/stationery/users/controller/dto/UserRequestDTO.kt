package com.example.stationery.users.controller.dto

import com.example.stationery.roles.Role
import com.example.stationery.roles.RoleRepository
import com.example.stationery.roles.RoleService
import com.example.stationery.users.User
import jakarta.persistence.EntityNotFoundException
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class UserRequestDTO(
    @field:NotNull
    val email: String?,

    @field:NotBlank
    val password: String?,

    @field:NotBlank
    val name: String?
) {
    fun toUser(roleService: RoleService): User {
        val roleUser = roleService.findByNameOrNull("USER")
            ?: throw EntityNotFoundException("Role 'USER' not found")

        return User(
            email = email!!,
            password = password!!,
            name = name!!
        ).apply { roles.add(roleUser) }
    }

}
