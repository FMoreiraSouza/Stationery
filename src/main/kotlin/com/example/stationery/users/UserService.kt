package com.example.stationery.users



import com.example.stationery.roles.RoleService
import com.example.stationery.security.JWT
import com.example.stationery.users.controller.dto.LoginResponseDTO
import com.example.stationery.users.controller.dto.UserResponseDTO
import jakarta.persistence.EntityNotFoundException
import org.apache.coyote.BadRequestException
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
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


    fun findByIdOrNull(id: Long): User? = userRepository.findByIdOrNull(id)
    fun delete(id: Long): Unit = userRepository.deleteById(id)

    fun addRole(id: Long, roleName: String): Boolean {
        val roleUpper = roleName.uppercase()
        val user = userRepository.findById(id).orElseThrow {
            EntityNotFoundException("Produto com id $id não encontrado.")
        }
        if (user.roles.any { it.name == roleUpper }) return false
        val role =
            roleService.findByNameOrNull(roleUpper) ?: throw BadRequestException("User ${roleUpper} not found")
        user.roles.add(role)
        userRepository.save(user)
        return true
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