package com.example.stationery.product.controller.dto

import com.example.stationery.product.entity.Product
import com.example.stationery.supplier.controller.dto.SupplierResponseDTO
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProductResponseDTO(
    val id: Long,
    val name: String,
    val price: Double,
    val stock: Int,
    val supplier: SupplierResponseDTO?
) {
    constructor(product: Product) : this(
        id = product.id!!,
        name = product.name,
        price = product.price,
        stock = product.stock,
        supplier = product.supplier?.let { SupplierResponseDTO(it) }
    )
}
