package com.anezin.melichallenge.core.actions

import com.anezin.melichallenge.core.domain.product.Product
import com.anezin.melichallenge.core.interfaces.repositories.SearchProductRepository
import javax.inject.Inject

class SearchProduct @Inject constructor(
    private val searchProductRepository: SearchProductRepository
) {
    suspend operator fun invoke(productName: String): List<Product> {
        return searchProductRepository.get(productName)
    }
}