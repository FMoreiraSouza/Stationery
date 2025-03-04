package com.example.stationery

import com.example.stationery.roles.Role
import com.example.stationery.roles.RoleRepository
import com.example.stationery.users.User
import com.example.stationery.users.UserRepository
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class Bootstrapper(
    private val roleRepository: RoleRepository,
    private val userRepository: UserRepository
) : ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        val adminRoleEntity = roleRepository.findByIdOrNull("ADMIN")
            ?: roleRepository.save(Role("ADMIN")).also {
                roleRepository.save(Role("USER"))
            }

        if (userRepository.findByRole("ADMIN").isEmpty()) {
            val adminUser = User(
                name = "Admin",
                password = "admin123",
                email = "admin@example.com"
            )
            adminUser.roles.add(adminRoleEntity)
            userRepository.save(adminUser)
        }
    }
}
