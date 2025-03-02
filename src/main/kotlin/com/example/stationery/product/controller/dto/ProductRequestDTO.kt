package com.example.stationery.product.controller.dto

import com.example.stationery.product.entity.ProductEntity
import com.example.stationery.supplier.entity.SupplierEntity
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero

data class ProductRequestDTO(

    @NotBlank(message = "O nome do produto não pode estar em branco")
    val name: String,

    @NotBlank(message = "A descrição do produto não pode estar em branco")
    val description: String,

    @Positive(message = "O preço do produto deve ser maior que zero")
    val price: Double,

    @PositiveOrZero(message = "O estoque do produto não pode ser negativo")
    val stock: Int,

) {
    fun toProduct() = ProductEntity(
        name = name,
        description = description,
        price = price,
        stock = stock
    )
}