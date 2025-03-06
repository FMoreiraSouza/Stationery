package com.example.stationery.supplier.entity

import com.example.stationery.product.entity.Product
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "suppliers")
data class Supplier(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @NotBlank(message = "O nome do fornecedor não pode estar em branco")
    var name: String,
    @NotBlank(message = "O fornecedor deve ter uma forma de contato")
    var contact: String,
    @OneToMany(mappedBy = "supplier", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val products: List<Product> = mutableListOf()
)
