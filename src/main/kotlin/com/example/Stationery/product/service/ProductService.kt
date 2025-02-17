package com.example.Stationery.product.service

import com.example.Stationery.product.entity.ProductEntity
import com.example.Stationery.product.repository.ProductRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun getAllProducts(): List<ProductEntity> = productRepository.findAll()

    fun getFilteredProductsByName(name: String?, pageRequest: PageRequest): List<ProductEntity> {
        // Chama o repositório passando o nome do produto, que pode ser nulo
        return if (name != null) {
            productRepository.findByNameContaining(name, pageRequest)
        } else {
            productRepository.findAll(pageRequest).content
        }
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
