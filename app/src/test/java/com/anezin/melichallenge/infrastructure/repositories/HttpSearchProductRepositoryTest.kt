package com.anezin.melichallenge.infrastructure.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anezin.melichallenge.core.domain.product.Product
import com.anezin.melichallenge.core.domain.product.ProductCondition
import com.anezin.melichallenge.core.domain.seller.Seller
import com.anezin.melichallenge.infrastructure.dto.ProductDto
import com.anezin.melichallenge.infrastructure.dto.SearchResponseDto
import com.anezin.melichallenge.infrastructure.dto.SellerDto
import com.anezin.melichallenge.infrastructure.exceptions.NotFoundException
import com.anezin.melichallenge.infrastructure.exceptions.ServerErrorException
import com.anezin.melichallenge.infrastructure.exceptions.UnknownHttpException
import com.anezin.melichallenge.infrastructure.http.RetrofitProductClient
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class HttpSearchProductRepositoryTest {
    private lateinit var repository: HttpSearchProductRepository
    private lateinit var retrofitClient: RetrofitProductClient

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private var result: List<Product>? = null

    @Before
    fun setup() {
        result = null
        retrofitClient = mockk(relaxed = true)
        repository = HttpSearchProductRepository(retrofitClient)

        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `given successful retrofit response, when get from repository, then get parsed (domain) products`() =
        runTest {
            coEvery { retrofitClient.getProducts(QUERY) } returns (API_RESPONSE)

            whenGetFromRepository()

            thenGetParsedProducts()
        }

    @Test
    fun `given empty retrofit response, when get from repository, then return empty list`() =
        runTest {
            coEvery { retrofitClient.getProducts(QUERY) } returns (SearchResponseDto(emptyList()))

            whenGetFromRepository()

            thenReturnEmptyList()
        }

    @Test(expected = ServerErrorException::class)
    fun `given 500 retrofit response, then handle exception and rethrow`() = runTest {
        coEvery { retrofitClient.getProducts(QUERY) } throws (HttpException(
            Response.error<ResponseBody>(
                500,
                "".toResponseBody()
            )
        ))

        whenGetFromRepository()
    }

    @Test(expected = NotFoundException::class)
    fun `given 404 retrofit response, then handle exception and rethrow`() = runTest {
        coEvery { retrofitClient.getProducts(QUERY) } throws (HttpException(
            Response.error<ResponseBody>(
                404,
                "".toResponseBody()
            )
        ))

        whenGetFromRepository()
    }

    @Test(expected = UnknownHttpException::class)
    fun `given 443 retrofit response, then handle exception and rethrow`() = runTest {
        coEvery { retrofitClient.getProducts(QUERY) } throws (HttpException(
            Response.error<ResponseBody>(
                443,
                "".toResponseBody()
            )
        ))

        whenGetFromRepository()
    }

    private suspend fun whenGetFromRepository() {
        result = repository.get(QUERY)
        testDispatcher.scheduler.advanceUntilIdle()
    }

    private fun thenGetParsedProducts() {
        assertEquals(EXPECTED_PARSED_PRODUCTS, result)
    }

    private fun thenReturnEmptyList() {
        assertEquals(emptyList<Product>(), result)
    }

    private companion object {
        const val QUERY = "query"
        val API_RESPONSE = SearchResponseDto(
            results = listOf(
                ProductDto(
                    title = "iPhone 13",
                    condition = "NEW",
                    thumbnail = "https://thumbnail.com/iphone.jpg",
                    price = 999.99,
                    seller = SellerDto(
                        nickname = "TechStore",
                    ),
                    availableQuantity = 10
                )
            )
        )
        val EXPECTED_PARSED_PRODUCTS = listOf(
            Product(
                title = "iPhone 13",
                condition = ProductCondition.New,
                thumbnail = "https://thumbnail.com/iphone.jpg",
                price = 999.99,
                seller = Seller(
                    nickname = "TechStore",
                ),
                availableQuantity = 10
            )
        )
    }
}