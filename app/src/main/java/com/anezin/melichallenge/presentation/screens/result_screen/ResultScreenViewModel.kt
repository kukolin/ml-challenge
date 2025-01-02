package com.anezin.melichallenge.presentation.screens.result_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anezin.melichallenge.core.actions.SearchProduct
import com.anezin.melichallenge.core.domain.product.Product
import com.anezin.melichallenge.infrastructure.exceptions.NotFoundException
import com.anezin.melichallenge.infrastructure.exceptions.ServerErrorException
import com.anezin.melichallenge.infrastructure.exceptions.UnknownHttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultScreenViewModel @Inject constructor(
    private val searchProduct: SearchProduct,
) : ViewModel() {
    private val _uiState = MutableStateFlow<ResultScreenState>(ResultScreenState.Loading)
    val uiState: StateFlow<ResultScreenState> = _uiState.asStateFlow()

    fun onInitialize(productQuery: String) {
        _uiState.value = ResultScreenState.Loading
        viewModelScope.launch {
            try {
                val products = searchProduct(productQuery)
                handleSearchSuccess(products)
            } catch (e: NotFoundException) {
                handleErrorWithState(ResultScreenState.NotFound, e)
            } catch (e: ServerErrorException) {
                handleErrorWithState(ResultScreenState.ServerError, e)
            } catch (e: UnknownHttpException) {
                handleErrorWithState(ResultScreenState.UnknownHttpError, e)
            } catch (e: Exception) {
                handleErrorWithState(ResultScreenState.UnknownError, e)
            }
        }
    }

    private fun handleSearchSuccess(products: List<Product>) {
        if (products.isEmpty()) {
            _uiState.value = ResultScreenState.Empty
        } else {
            _uiState.value = ResultScreenState.Success(products)
        }
    }

    private fun handleErrorWithState(newState: ResultScreenState, e: Exception) {
        Log.e("Search Products Error", "Error Message: ${e.message}")
        _uiState.value = newState
    }

    override fun onCleared() {
        Log.d("Result Screen ViewModel", "onCleared")
        super.onCleared()
    }
}