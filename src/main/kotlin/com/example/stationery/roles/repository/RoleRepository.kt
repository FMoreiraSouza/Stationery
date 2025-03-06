package com.example.stationery.roles.repository

import com.example.stationery.roles.entity.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, String>
