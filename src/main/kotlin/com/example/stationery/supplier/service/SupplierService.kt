package com.example.stationery.supplier.service

import com.example.stationery.supplier.entity.SupplierEntity
import com.example.stationery.supplier.repository.SupplierRepository
import org.springframework.stereotype.Service

@Service
class SupplierService(private val supplierRepository: SupplierRepository) {

    fun createSupplier(supplierEntity: SupplierEntity): SupplierEntity = supplierRepository.save(supplierEntity)

    fun findAllSuppliers(): List<SupplierEntity> = supplierRepository.findAll()
}
