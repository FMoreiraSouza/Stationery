package com.example.stationery.product.service

import com.example.stationery.product.entity.Product
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

    fun getAllProducts(sort: Sort): List<Product> {
        return productRepository.findAll(sort)
    }

    fun getProductById(id: Long): Product {
        return productRepository.findByIdWithSupplier(id)
            .orElseThrow { EntityNotFoundException("Produto com id $id não encontrado.") }
    }

    fun createProduct(product: Product): Product = productRepository.save(product)

    fun restockingProduct(id: Long, updatedProduct: Product): Product {
        val existingProduct = getProductById(id)
        val productToSave = existingProduct.copy(
            name = updatedProduct.name,
            description = updatedProduct.description,
            price = updatedProduct.price,
            stock = updatedProduct.stock,
        )
        return productRepository.save(productToSave)
    }

    fun purchaseProduct(id: Long, quantity: Int): Product {
        val existingProduct = getProductById(id)

        if (existingProduct.stock < quantity) {
            throw IllegalArgumentException("Estoque insuficiente para realizar a compra")
        }
        val newStock = existingProduct.stock - quantity
        val productToSave = existingProduct.copy(stock = newStock)
        return productRepository.save(productToSave)
    }

    fun associateProductWithSupplier(productId: Long, supplierId: Long): Product {
        val product = productRepository.findById(productId).orElseThrow {
            EntityNotFoundException("Produto com id $productId não encontrado.")
        }
        val supplier = supplierRepository.findById(supplierId).orElseThrow {
            EntityNotFoundException("Fornecedor com id $supplierId não encontrado.")
        }
        product.supplier = supplier
        return productRepository.save(product)
    }



    fun deleteProduct(id: Long) {
        if (!productRepository.existsById(id)) {
            throw RuntimeException("Product not found")
        }
        productRepository.deleteById(id)
    }
}
