package com.example.stationery.product.entity

import com.example.stationery.supplier.entity.SupplierEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "products")
data class ProductEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @field:NotBlank(message = "O nome do produto não pode estar em branco")
    val name: String,

    @field:NotBlank(message = "A descrição do produto não pode estar em branco")
    val description: String,

    @field:Positive(message = "O preço do produto deve ser maior que zero")
    val price: Double,

    @field:PositiveOrZero(message = "O estoque do produto não pode ser negativo")
    val stock: Int,

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    val supplier: SupplierEntity?
)
