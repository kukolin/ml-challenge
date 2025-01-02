package com.anezin.melichallenge.core.domain.product

import com.anezin.melichallenge.core.domain.seller.Seller
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val title: String,
    val condition: ProductCondition,
    val thumbnail: String,
    val price: Double,
    val seller: Seller,
    val availableQuantity: Int,
)