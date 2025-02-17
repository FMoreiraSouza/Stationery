package com.example.Stationery.product.repository

import com.example.Stationery.product.entity.ProductEntity
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<ProductEntity, Long> {
    override fun findAll(sort: Sort): List<ProductEntity>
}
