package com.example.stationery

import com.example.stationery.roles.entity.Role
import com.example.stationery.roles.repository.RoleRepository
import com.example.stationery.users.User
import com.example.stationery.users.UserRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class Bootstrapper(
    private val roleRepository: RoleRepository,
    private val userRepository: UserRepository,
    @Qualifier("defaultAdmin") private val adminUser: User,
) : ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        val adminRole = roleRepository.findByIdOrNull("ADMIN")
            ?: roleRepository.save(Role("ADMIN")).also {
                roleRepository.save(Role("USER"))
            }

        if (userRepository.findByRole("ADMIN").isEmpty()) {
            adminUser.roles.add(adminRole)
            userRepository.save(adminUser)
        }
    }
}
