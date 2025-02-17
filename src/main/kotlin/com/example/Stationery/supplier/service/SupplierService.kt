package com.example.Stationery.supplier.service

import com.example.Stationery.supplier.entity.SupplierEntity
import com.example.Stationery.supplier.repository.SupplierRepository
import org.springframework.stereotype.Service

@Service
class SupplierService(private val supplierRepository: SupplierRepository) {

    fun createSupplier(supplierEntity: SupplierEntity): SupplierEntity {
        return supplierRepository.save(supplierEntity)
    }

    fun getSupplierById(id: Long): SupplierEntity {
        return supplierRepository.findById(id).orElseThrow { IllegalArgumentException("Supplier not found") }
    }

}
