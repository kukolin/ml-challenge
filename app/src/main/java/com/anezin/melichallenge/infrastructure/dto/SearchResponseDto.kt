package com.anezin.melichallenge.infrastructure.dto

import com.google.gson.annotations.SerializedName

data class SearchResponseDto(
    @SerializedName("results")
    val results: List<ProductDto>
)