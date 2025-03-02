package com.example.stationery.supplier.controller

import com.example.stationery.product.controller.dto.ProductRequestDTO
import com.example.stationery.supplier.controller.dto.SupplierDTO
import com.example.stationery.supplier.entity.SupplierEntity
import com.example.stationery.supplier.service.SupplierService
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

//    @GetMapping("/{id}")
//    fun getSupplierById(@PathVariable id: Long): ResponseEntity<SupplierDTO> {
//        val supplier = supplierService.getSupplierById(id)
//        val productRequestDTOS = supplier.productEntities.map { product ->
//            ProductRequestDTO(
//                id = product.id!!,
//                name = product.name,
//                description = product.description,
//                price = product.price,
//                stock = product.stock,
//                supplier = product.supplier.id,
//            )
//        }
//
//        val supplierDTO = SupplierDTO(
//            id = supplier.id!!,
//            name = supplier.name!!,
//            contact = supplier.contact!!,
//            products = productRequestDTOS
//        )
//        return ResponseEntity.ok(supplierDTO)
//    }
}
