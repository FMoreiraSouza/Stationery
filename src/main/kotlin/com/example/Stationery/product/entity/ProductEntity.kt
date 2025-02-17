package com.example.Stationery.product.entity

import com.example.Stationery.supplier.entity.SupplierEntity
import jakarta.persistence.*

@Entity
@Table(name = "products")
data class ProductEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,
    val description: String,
    val price: Double,
    val stock: Int,

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    val supplier: SupplierEntity
)
