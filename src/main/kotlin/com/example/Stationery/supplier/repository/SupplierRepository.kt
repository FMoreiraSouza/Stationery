package com.example.Stationery.supplier.repository

import com.example.Stationery.supplier.entity.SupplierEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SupplierRepository : JpaRepository<SupplierEntity, Long>
