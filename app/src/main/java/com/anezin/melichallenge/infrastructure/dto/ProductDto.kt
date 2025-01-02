package com.anezin.melichallenge.infrastructure.dto

import com.anezin.melichallenge.core.domain.product.Product
import com.anezin.melichallenge.core.domain.product.ProductCondition
import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("title")
    val title: String,

    @SerializedName("condition")
    val condition: String,

    @SerializedName("thumbnail")
    val thumbnail: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("seller")
    val seller: SellerDto,

    @SerializedName("available_quantity")
    val availableQuantity: Int,
)

fun ProductDto.toDomain(): Product {
    return Product(
        title = title,
        condition = ProductCondition.fromString(condition),
        thumbnail = thumbnail,
        price = price,
        seller = seller.toDomain(),
        availableQuantity = availableQuantity,
    )
}
