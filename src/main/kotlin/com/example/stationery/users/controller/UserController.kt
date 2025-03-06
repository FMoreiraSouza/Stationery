package com.example.stationery.users.controller

import com.example.stationery.users.UserService
import com.example.stationery.users.controller.dto.LoginRequestDTO
import com.example.stationery.users.controller.dto.UserRequestDTO
import com.example.stationery.users.controller.dto.UserResponseDTO
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {

    @PostMapping
    fun insert(@RequestBody @Valid userDTO: UserRequestDTO, authentication: Authentication?): ResponseEntity<UserResponseDTO> {
        if (authentication != null && authentication.authorities.any { it.authority == "ROLE_ADMIN" }) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }

        val user = userDTO.toUser(userService.roleService)
        val savedUser = userService.insert(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseDTO(savedUser))
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "StationeryServer")
    fun findAll(
        @RequestParam role: String? = null
    ): ResponseEntity<List<UserResponseDTO>> {
        return userService.findAll(role)
            .map { UserResponseDTO(it) }
            .let { ResponseEntity.ok(it) }
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody user: LoginRequestDTO) =
        userService.login(user.email!!, user.password!!)
            ?.let { ResponseEntity.ok().body(it) }
            ?: ResponseEntity.notFound().build()
}