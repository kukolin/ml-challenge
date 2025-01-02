package com.anezin.melichallenge.core.interfaces.repositories

import com.anezin.melichallenge.core.domain.product.Product

interface SearchProductRepository {
    suspend fun get(query: String) : List<Product>
}