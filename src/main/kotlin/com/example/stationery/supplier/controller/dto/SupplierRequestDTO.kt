package com.example.stationery.supplier.controller.dto

import com.example.stationery.supplier.entity.Supplier
import jakarta.validation.constraints.NotBlank

data class SupplierRequestDTO(
    @NotBlank(message = "O nome do fornecedor não pode estar em branco")
    val name: String,

    @NotBlank(message = "O fornecedor deve ter uma forma de contato")
    val contact: String
){
    fun toSupplier() = Supplier(
        name = name,
        contact = contact
    )
}
