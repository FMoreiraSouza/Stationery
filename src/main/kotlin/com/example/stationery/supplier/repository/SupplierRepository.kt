package com.example.stationery.supplier.repository

import com.example.stationery.supplier.entity.SupplierEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SupplierRepository : JpaRepository<SupplierEntity, Long>
