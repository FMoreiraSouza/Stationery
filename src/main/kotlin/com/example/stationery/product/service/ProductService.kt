package com.example.stationery.product.service

import com.example.stationery.product.entity.ProductEntity
import com.example.stationery.product.repository.ProductRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun getAllProducts(sort: Sort): List<ProductEntity> {
        return productRepository.findAll(sort)
    }
    fun getProductById(id: Long): ProductEntity =
        productRepository.findById(id).orElseThrow { RuntimeException("Product not found") }

    fun createProduct(productEntity: ProductEntity): ProductEntity = productRepository.save(productEntity)

    fun updateProduct(id: Long, updatedProductEntity: ProductEntity): ProductEntity {
        val existingProduct = getProductById(id)
        val productToSave = existingProduct.copy(
            name = updatedProductEntity.name,
            description = updatedProductEntity.description,
            price = updatedProductEntity.price,
            stock = updatedProductEntity.stock,
        )
        return productRepository.save(productToSave)
    }

    fun deleteProduct(id: Long) {
        if (!productRepository.existsById(id)) {
            throw RuntimeException("Product not found")
        }
        productRepository.deleteById(id)
    }
}
