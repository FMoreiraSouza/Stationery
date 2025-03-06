package com.example.stationery.supplier.controller

import com.example.stationery.supplier.controller.dto.SupplierRequestDTO
import com.example.stationery.supplier.controller.dto.SupplierResponseDTO
import com.example.stationery.supplier.service.SupplierService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/suppliers")
class SupplierController(private val supplierService: SupplierService) {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "StationeryServer")
    fun getAllSuppliers(): ResponseEntity<List<SupplierResponseDTO>> {
        val suppliers = supplierService.findAllSuppliers()
        val supplierDtos = suppliers.map { SupplierResponseDTO(it) }
        return ResponseEntity.ok(supplierDtos)
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "StationeryServer")
    fun createSupplier(@RequestBody supplier: SupplierRequestDTO): ResponseEntity<SupplierResponseDTO> {
        val createdSupplier = supplierService.createSupplier(supplier.toSupplier())
        return ResponseEntity.ok(SupplierResponseDTO(createdSupplier))
    }
}
