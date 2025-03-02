package com.example.authServer.users.controller.responses

import com.example.stationery.product.entity.ProductEntity
import com.example.stationery.supplier.entity.SupplierEntity

data class ProductResponseDTO(
    val id: Long,
    val name: String,
    val price: Double,
    val stock: Int,
    val supplier: SupplierEntity?
) {
    constructor(product: ProductEntity) : this(
        id = product.id!!,
         product.name,
        product.price,
        product.stock,
        product.supplier
    )
}
