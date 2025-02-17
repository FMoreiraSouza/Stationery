package com.example.stationery.product.controller.dto

data class ProductDTO(
    val id: Long,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val supplier: Any?
)
