package com.example.stationery.supplier.controller.dto

import com.example.stationery.supplier.entity.Supplier

data class SupplierResponseDTO(
    val id: Long,
    val name: String,
    val contact: String,
) {
    constructor(supplier: Supplier) : this(
        id = supplier.id!!,
        supplier.name,
        supplier.contact
    )
}
