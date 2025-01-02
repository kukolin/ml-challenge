package com.anezin.melichallenge.infrastructure.dto

import com.anezin.melichallenge.core.domain.seller.Seller
import com.google.gson.annotations.SerializedName

data class SellerDto(
    @SerializedName("nickname")
    val nickname: String
)


fun SellerDto.toDomain(): Seller {
    return Seller(nickname = nickname)
}