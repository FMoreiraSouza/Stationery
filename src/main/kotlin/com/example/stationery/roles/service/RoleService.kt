package com.example.stationery.roles.service

import com.example.stationery.roles.repository.RoleRepository
import com.example.stationery.roles.entity.Role
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RoleService(
    val roleRepository: RoleRepository
) {
    fun findAll(): MutableList<Role> = roleRepository.findAll(Sort.by("name"))
    fun findByNameOrNull(name: String) = roleRepository.findByIdOrNull(name)
}


