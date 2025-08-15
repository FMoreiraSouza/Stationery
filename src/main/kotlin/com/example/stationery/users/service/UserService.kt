package com.example.stationery.users.service

import com.example.stationery.roles.service.RoleService
import com.example.stationery.security.JWT
import com.example.stationery.users.controller.dto.response.LoginResponseDTO
import com.example.stationery.users.controller.dto.response.UserResponseDTO
import com.example.stationery.users.entity.User
import com.example.stationery.users.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository: UserRepository,
    val roleService: RoleService,
    private val jwt: JWT
) {
    fun insert(user: User): User = userRepository.save(user)

    fun findAll(role: String?): List<User> {
        if(!role.isNullOrBlank())
            return userRepository.findByRole(role)
        return userRepository.findAll(Sort.by(("name")))
    }

    fun login(email: String, password:String): LoginResponseDTO?{
        val user = userRepository.findByEmail(email) ?: return null
        if(user.password != password) return null
        log.info("User logged i. id=${user.id}, name=${user.name}")
        return LoginResponseDTO(
            token = jwt.createToken(user),
            user = UserResponseDTO(user),
        )
    }

    companion object{
        private val log = LoggerFactory.getLogger((UserService::class.java))
    }
}