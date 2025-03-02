package com.example.authServer.users.controller.responses

import com.example.stationery.supplier.entity.SupplierEntity

data class SupplierResponseDTO(
    val id: Long,
    val name: String,
    val contact: String,
) {
    constructor(supplier: SupplierEntity) : this(
        id = supplier.id!!,
        supplier.name,
        supplier.contact
    )
}
