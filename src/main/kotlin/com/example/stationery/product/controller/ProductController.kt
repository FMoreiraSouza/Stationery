package com.example.stationery.product.controller

import com.example.stationery.product.controller.dto.ProductDTO
import com.example.stationery.product.entity.ProductEntity
import com.example.stationery.product.service.ProductService
import com.example.stationery.supplier.controller.dto.SupplierDTO
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(private val productService: ProductService) {

    @GetMapping
    fun getSortedProducts(
        @RequestParam(name = "sortOrder", defaultValue = "ASC") sortOrder: String
    ): ResponseEntity<List<ProductDTO>> {
        val sortDirection = if (sortOrder.equals("DESC", ignoreCase = true)) Sort.Direction.DESC else Sort.Direction.ASC
        val sort = Sort.by(sortDirection, "id")

        val sortedProducts = productService.getAllProducts(sort)

        val productDTOs = sortedProducts.map { product ->
            ProductDTO(
                id = product.id!!,
                name = product.name,
                description = product.description,
                price = product.price,
                stock = product.stock,
                supplier = product.supplier.id!!
            )
        }

        return ResponseEntity.ok(productDTOs)
    }



    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<ProductDTO> {
        val product = productService.getProductById(id)
        val productDTO = ProductDTO(
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
        return ResponseEntity.ok(productDTO)
    }
    @PostMapping
    fun createProduct(@RequestBody productEntity: ProductEntity): ResponseEntity<ProductDTO> {
        val createdProduct = productService.createProduct(productEntity)
        val productDTO = ProductDTO(
            id = createdProduct.id!!,
            name = createdProduct.name,
            description = createdProduct.description,
            price = createdProduct.price,
            stock = createdProduct.stock,
            supplier = createdProduct.supplier.id!!
        )
        return ResponseEntity.ok(productDTO)
    }

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: Long, @RequestBody productEntity: ProductEntity): ResponseEntity<ProductDTO> {
        val updatedProduct = productService.updateProduct(id, productEntity)
        val productDTO = ProductDTO(
            id = updatedProduct.id!!,
            name = updatedProduct.name,
            description = updatedProduct.description,
            price = updatedProduct.price,
            stock = updatedProduct.stock,
            supplier =  updatedProduct.supplier.id,
        )
        return ResponseEntity.ok(productDTO)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Void> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }
}
