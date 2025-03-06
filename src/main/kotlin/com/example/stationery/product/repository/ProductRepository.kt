package com.example.stationery.product.repository

import com.example.stationery.product.entity.Product
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    override fun findAll(sort: Sort): List<Product>
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.supplier WHERE p.id = :id")
    fun findByIdWithSupplier(@Param("id") id: Long): Optional<Product>
}

