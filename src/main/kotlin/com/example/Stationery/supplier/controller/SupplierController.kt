package com.example.Stationery.supplier.controller

import com.example.Stationery.product.controller.dto.ProductDTO
import com.example.Stationery.supplier.controller.dto.SupplierDTO
import com.example.Stationery.supplier.entity.SupplierEntity
import com.example.Stationery.supplier.service.SupplierService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/suppliers")
class SupplierController(private val supplierService: SupplierService) {

    @PostMapping
    fun createSupplier(@RequestBody supplierEntity: SupplierEntity): ResponseEntity<SupplierDTO> {
        val createdSupplier = supplierService.createSupplier(supplierEntity)
        val supplierDTO = SupplierDTO(
            id = createdSupplier.id!!,
            name = createdSupplier.name!!,
            contact = createdSupplier.contact!!,
        )
        return ResponseEntity.ok(supplierDTO)
    }

    @GetMapping("/{id}")
    fun getSupplierById(@PathVariable id: Long): ResponseEntity<SupplierDTO> {
        val supplier = supplierService.getSupplierById(id)
        val productDTOs = supplier.productEntities.map { product ->
            ProductDTO(
                id = product.id!!,
                name = product.name,
                description = product.description,
                price = product.price,
                stock = product.stock,
                supplier = SupplierDTO(
                    id = product.supplier.id!!,
                    name = product.supplier.name!!,
                    contact = product.supplier.contact!!,

                    )
            )
        }

        val supplierDTO = SupplierDTO(
            id = supplier.id!!,
            name = supplier.name!!,
            contact = supplier.contact!!,
            products = productDTOs
        )

        return ResponseEntity.ok(supplierDTO)
    }
}
