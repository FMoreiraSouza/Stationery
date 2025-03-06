package com.example.stationery.product.service

import com.example.stationery.product.entity.ProductEntity
import com.example.stationery.product.repository.ProductRepository
import com.example.stationery.supplier.repository.SupplierRepository

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val supplierRepository: SupplierRepository
) {

    fun getAllProducts(sort: Sort): List<ProductEntity> {
        return productRepository.findAll(sort)
    }

    fun getProductById(id: Long): ProductEntity {
        return productRepository.findByIdWithSupplier(id)
            .orElseThrow { EntityNotFoundException("Produto com id $id não encontrado.") }
    }


    fun createProduct(productEntity: ProductEntity): ProductEntity = productRepository.save(productEntity)

    fun restockingProduct(id: Long, updatedProductEntity: ProductEntity): ProductEntity {
        val existingProduct = getProductById(id)
        val productToSave = existingProduct.copy(
            name = updatedProductEntity.name,
            description = updatedProductEntity.description,
            price = updatedProductEntity.price,
            stock = updatedProductEntity.stock,
        )
        return productRepository.save(productToSave)
    }

    fun purchaseProduct(id: Long, quantity: Int): ProductEntity {
        val existingProduct = getProductById(id)

        if (existingProduct.stock < quantity) {
            throw IllegalArgumentException("Estoque insuficiente para realizar a compra")
        }

        val newStock = existingProduct.stock - quantity
        val productToSave = existingProduct.copy(stock = newStock)

        return productRepository.save(productToSave)
    }


    fun associateProductWithSupplier(productId: Long, supplierId: Long): ProductEntity {
        // Buscar o produto pelo id
        val product = productRepository.findById(productId).orElseThrow {
            EntityNotFoundException("Produto com id $productId não encontrado.")
        }

        // Buscar o fornecedor pelo id
        val supplier = supplierRepository.findById(supplierId).orElseThrow {
            EntityNotFoundException("Fornecedor com id $supplierId não encontrado.")
        }

        // Associar o fornecedor ao produto
        product.supplier = supplier

        // Salvar o produto com o fornecedor associado
        return productRepository.save(product)
    }



    fun deleteProduct(id: Long) {
        if (!productRepository.existsById(id)) {
            throw RuntimeException("Product not found")
        }
        productRepository.deleteById(id)
    }
}
