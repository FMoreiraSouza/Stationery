package com.example.stationery.supplier.controller.dto

import com.example.stationery.product.controller.dto.ProductRequestDTO

data class SupplierDTO(
    val id: Long,
    val name: String? = null,
    val contact: String? = null,
    val products: List<ProductRequestDTO> = emptyList()
)
