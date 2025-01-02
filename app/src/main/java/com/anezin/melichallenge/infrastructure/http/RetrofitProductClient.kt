package com.anezin.melichallenge.infrastructure.http

import com.anezin.melichallenge.infrastructure.dto.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitProductClient {
    @GET("/sites/MLA/search")
    suspend fun getProducts(@Query("q") query: String) : SearchResponseDto
}