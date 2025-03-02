package com.example.authServer.users.controller.responses

import com.example.stationery.product.entity.ProductEntity
import com.example.stationery.supplier.entity.SupplierEntity

data class ProductResponseDTO(
    val id: Long,
    val name: String,
    val price: Double,
    val stock: Int,
    val supplier: SupplierResponseDTO? // Novo campo para o fornecedor
) {
    constructor(product: ProductEntity) : this(
        id = product.id!!,
        name = product.name,
        price = product.price,
        stock = product.stock,
        supplier = product.supplier?.let { SupplierResponseDTO(it) } // Preenche o fornecedor se não for nulo
    )
}
