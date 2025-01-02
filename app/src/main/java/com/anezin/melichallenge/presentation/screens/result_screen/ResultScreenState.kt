package com.anezin.melichallenge.presentation.screens.result_screen

import com.anezin.melichallenge.core.domain.product.Product

sealed class ResultScreenState {
    data class Success(val products: List<Product>) : ResultScreenState()
    data object Loading : ResultScreenState()
    data object Empty : ResultScreenState()
    data object NotFound : ResultScreenState()
    data object ServerError : ResultScreenState()
    data object UnknownHttpError : ResultScreenState()
    data object UnknownError : ResultScreenState()
}