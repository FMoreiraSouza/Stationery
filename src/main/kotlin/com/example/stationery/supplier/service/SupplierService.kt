package com.example.stationery.supplier.service

import com.example.stationery.supplier.entity.Supplier
import com.example.stationery.supplier.repository.SupplierRepository
import org.springframework.stereotype.Service

@Service
class SupplierService(private val supplierRepository: SupplierRepository) {
    fun createSupplier(supplier: Supplier): Supplier = supplierRepository.save(supplier)
    fun findAllSuppliers(): List<Supplier> = supplierRepository.findAll()
}
