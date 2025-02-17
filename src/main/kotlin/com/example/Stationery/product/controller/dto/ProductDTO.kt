package com.example.Stationery.product.controller.dto

import com.example.Stationery.supplier.controller.dto.SupplierDTO

data class ProductDTO(
    val id: Long,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val supplier: SupplierDTO?
)
