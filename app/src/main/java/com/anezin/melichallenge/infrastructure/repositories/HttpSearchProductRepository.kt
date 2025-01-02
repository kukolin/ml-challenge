package com.anezin.melichallenge.infrastructure.repositories

import com.anezin.melichallenge.core.domain.product.Product
import com.anezin.melichallenge.core.interfaces.repositories.SearchProductRepository
import com.anezin.melichallenge.infrastructure.dto.toDomain
import com.anezin.melichallenge.infrastructure.exceptions.NotFoundException
import com.anezin.melichallenge.infrastructure.exceptions.ServerErrorException
import com.anezin.melichallenge.infrastructure.exceptions.UnknownHttpException
import com.anezin.melichallenge.infrastructure.http.RetrofitProductClient
import retrofit2.HttpException
import javax.inject.Inject

class HttpSearchProductRepository @Inject constructor(
    private val retrofitClient: RetrofitProductClient
) : SearchProductRepository {

    override suspend fun get(query: String): List<Product> {
        return try {
            val response = retrofitClient.getProducts(query)
            response.results.map { it.toDomain() }
        } catch (e: HttpException) {
            when (e.code()) {
                404 -> throw NotFoundException(e.message ?: "No error message")
                500 -> throw ServerErrorException(e.message ?: "No error message")
                else -> throw UnknownHttpException(e.message ?: "No error message")
            }
        } catch (e: Exception) {
            throw Exception("Error inesperado")
        }
    }
}