package com.example.Stationery.supplier.controller.dto

import com.example.Stationery.product.controller.dto.ProductDTO

data class SupplierDTO(
    val id: Long,
    val name: String? = null,
    val contact: String? = null,
    val products: List<ProductDTO> = emptyList()
)
