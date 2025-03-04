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
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {

    @PostMapping
    fun insert(@RequestBody @Valid user: UserRequestDTO) =
        userService.insert(user.toUser())
            .let { UserResponseDTO(it) }
            .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }

    @GetMapping
    fun findAll(
        @RequestParam role: String? = null
    ): ResponseEntity<List<UserResponseDTO>> {
        return userService.findAll(role)
            .map { UserResponseDTO(it) }
            .let { ResponseEntity.ok(it) }
    }

    @PutMapping(("/{id}/roles/{role}"))
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "StationeryServer")
    fun grant(@PathVariable id: Long, @PathVariable role: String): ResponseEntity<Void> =
        if (userService.addRole(id, role)) ResponseEntity.ok().build()
        else ResponseEntity.noContent().build()

    @PostMapping("/login")
    fun login(@Valid @RequestBody user: LoginRequestDTO) =
        userService.login(user.email!!, user.password!!)
            ?.let { ResponseEntity.ok().body(it) }
            ?: ResponseEntity.notFound().build()
}