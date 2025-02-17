package com.example.Stationery.supplier.entity

import com.example.Stationery.product.entity.ProductEntity
import jakarta.persistence.*

@Entity
@Table(name = "suppliers")
data class SupplierEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var name: String? = null,
    var contact: String? = null,

    @OneToMany(mappedBy = "supplier", cascade = [CascadeType.ALL])
    val productEntities: List<ProductEntity> = mutableListOf()
)
