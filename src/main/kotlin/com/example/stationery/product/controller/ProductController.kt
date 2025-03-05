package com.example.stationery.product.controller

import com.example.authServer.users.controller.responses.ProductResponseDTO
import com.example.stationery.product.controller.dto.ProductRequestDTO
import com.example.stationery.product.entity.ProductEntity
import com.example.stationery.product.service.ProductService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(private val productService: ProductService) {

    @GetMapping
    fun getSortedProducts(
        @RequestParam(name = "sortOrder", defaultValue = "ASC") sortOrder: String
    ): ResponseEntity<List<ProductResponseDTO>> {
        val sortDirection = if (sortOrder.equals("DESC", ignoreCase = true)) Sort.Direction.DESC else Sort.Direction.ASC
        val sort = Sort.by(sortDirection, "id")

        val sortedProducts = productService.getAllProducts(sort)

        val products = sortedProducts
            .map { ProductResponseDTO(it) }
            .let { ResponseEntity.ok(it) }

        return products
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<ProductResponseDTO> {
        val product = productService.getProductById(id)
        return product
            .let { ProductResponseDTO(it) }
            .let { ResponseEntity.ok(it) }
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "StationeryServer")
    fun createProduct(@RequestBody product: ProductRequestDTO): ResponseEntity<ProductResponseDTO> {
        val createdProduct = productService.createProduct(product.toProduct())
        return createdProduct
            .let { ProductResponseDTO(it) }
            .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "StationeryServer")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody productEntity: ProductEntity
    ): ResponseEntity<ProductResponseDTO> {
        val updatedProduct = productService.updateProduct(id, productEntity)
            return updatedProduct
            .let { ProductResponseDTO(it) }
            .let { ResponseEntity.ok(it) }
    }

    @PutMapping("/{productId}/associate")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "StationeryServer")
    fun associateProductWithSupplier(
        @PathVariable productId: Long,
        @RequestParam supplierId: Long
    ): ResponseEntity<ProductResponseDTO> {
        val updatedProduct = productService.associateProductWithSupplier(productId, supplierId)
        return updatedProduct
            .let { ProductResponseDTO(it) }
            .let { ResponseEntity.ok(it) }
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "StationeryServer")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Void> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }
}
