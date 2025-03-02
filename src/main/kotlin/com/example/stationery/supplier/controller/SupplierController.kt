package com.example.stationery.supplier.controller

import SupplierRequestDTO
import com.example.authServer.users.controller.responses.SupplierResponseDTO
import com.example.stationery.supplier.service.SupplierService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/suppliers")
class SupplierController(private val supplierService: SupplierService) {

    @GetMapping
    fun getAllSuppliers(): ResponseEntity<List<SupplierResponseDTO>> {
        val suppliers = supplierService.findAllSuppliers()
        val supplierDtos = suppliers.map { SupplierResponseDTO(it) }
        return ResponseEntity.ok(supplierDtos)
    }

    @PostMapping
    fun createSupplier(@RequestBody supplier: SupplierRequestDTO): ResponseEntity<SupplierResponseDTO> {
        val createdSupplier = supplierService.createSupplier(supplier.toSupplier())
        return ResponseEntity.ok(SupplierResponseDTO(createdSupplier))
    }

}
