package com.anezin.melichallenge.presentation.result_screen

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anezin.melichallenge.core.actions.SearchProduct
import com.anezin.melichallenge.core.domain.product.Product
import com.anezin.melichallenge.core.domain.product.ProductCondition
import com.anezin.melichallenge.core.domain.seller.Seller
import com.anezin.melichallenge.infrastructure.exceptions.NotFoundException
import com.anezin.melichallenge.infrastructure.exceptions.ServerErrorException
import com.anezin.melichallenge.infrastructure.exceptions.UnknownHttpException
import com.anezin.melichallenge.presentation.screens.result_screen.ResultScreenState
import com.anezin.melichallenge.presentation.screens.result_screen.ResultScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import io.mockk.*

@ExperimentalCoroutinesApi
class ResultScreenViewModelTest {

    private lateinit var viewModel: ResultScreenViewModel
    private lateinit var searchProduct: SearchProduct
    private lateinit var navigateToDetails: (Product) -> Unit

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        searchProduct = mockk(relaxed = true)
        navigateToDetails = mockk(relaxed = true)
        viewModel = ResultScreenViewModel(searchProduct)

        Dispatchers.setMain(testDispatcher)
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
    }

    @Test
    fun `should emit Loading state initially`() {
        assertEquals(ResultScreenState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `should emit Success state when onInitialize is successful`() = runTest {
        val mockProductList = listOf(PRODUCT, PRODUCT)
        givenAValidSearchProductForProductList(mockProductList)

        whenSearchWithQuery(QUERY)

        thenUiStateIsSuccessWithProductList(mockProductList)
    }


    @Test
    fun `should call searchProduct when onInitialize is invoked`() = runTest {
        givenAValidSearchProductForQuery(QUERY)

        whenSearchWithQuery(QUERY)

        thenSearchProductActionIsCalledWithQuery(QUERY)
    }

    @Test
    fun `should emit Unknown Error state when onInitialize fails with unknown error`() = runTest {
        givenAFailingSearchProductWithExceptionType(Exception())

        whenSearchWithQuery(QUERY)

        thenUiStateIs(ResultScreenState.UnknownError)
    }

    @Test
    fun `should emit Not Found Error state when onInitialize fails`() = runTest {
        givenAFailingSearchProductWithExceptionType(NotFoundException(""))

        whenSearchWithQuery(QUERY)

        thenUiStateIs(ResultScreenState.NotFound)
    }

    @Test
    fun `should emit Server Error state when onInitialize fails`() = runTest {
        givenAFailingSearchProductWithExceptionType(ServerErrorException(""))

        whenSearchWithQuery(QUERY)

        thenUiStateIs(ResultScreenState.ServerError)
    }

    @Test
    fun `should emit Http Error state when onInitialize fails`() = runTest {
        givenAFailingSearchProductWithExceptionType(UnknownHttpException(""))

        whenSearchWithQuery(QUERY)

        thenUiStateIs(ResultScreenState.UnknownHttpError)
    }

    //GIVEN methods

    private fun givenAFailingSearchProductWithExceptionType(errorType: Exception) {
        coEvery { searchProduct(QUERY) } throws errorType
    }

    private fun givenAValidSearchProductForQuery(productQuery: String) {
        val mockProductList = listOf(PRODUCT, PRODUCT)
        coEvery { searchProduct(productQuery) } returns mockProductList
    }

    private fun givenAValidSearchProductForProductList(mockProductList: List<Product>) {
        coEvery { searchProduct(QUERY) } returns mockProductList
    }

    //WHEN methods

    private fun whenSearchWithQuery(productQuery: String) {
        viewModel.onInitialize(productQuery)
        testDispatcher.scheduler.advanceUntilIdle()
    }

    //THEN methods

    private fun thenSearchProductActionIsCalledWithQuery(productQuery: String) {
        coVerify { searchProduct(productQuery) }
    }

    private fun thenUiStateIs(newState: ResultScreenState) {
        assertEquals(newState, viewModel.uiState.value)
    }

    private fun thenUiStateIsSuccessWithProductList(mockProductList: List<Product>) {
        assertEquals(ResultScreenState.Success(mockProductList), viewModel.uiState.value)
    }

    private fun thenNavigationCalledWithProduct(product: Product) {
        verify(exactly = 1) { navigateToDetails(product) }
    }

    private companion object {
        const val QUERY = "query"
        val PRODUCT = Product(
            "title",
            ProductCondition.New,
            "thumbnail",
            15.0,
            Seller("nickname"),
            1
        )
    }
}
