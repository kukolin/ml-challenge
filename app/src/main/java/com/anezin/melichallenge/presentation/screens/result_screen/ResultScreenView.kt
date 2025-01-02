package com.anezin.melichallenge.presentation.screens.result_screen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.anezin.melichallenge.presentation.composables.ProductList
import com.anezin.melichallenge.presentation.composables.state_composables.EmptyState
import com.anezin.melichallenge.presentation.composables.state_composables.ErrorState
import com.anezin.melichallenge.presentation.composables.state_composables.LoadingState
import com.anezin.melichallenge.presentation.composables.state_composables.NotFoundState
import com.anezin.melichallenge.presentation.composables.state_composables.ServerErrorState
import com.anezin.melichallenge.presentation.composables.state_composables.UnknownHttpState
import com.anezin.melichallenge.presentation.screens.Screens
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class ResultScreenView {
    @Composable
    fun Build(
        productQuery: String,
        navController: NavHostController,
        viewModel: ResultScreenViewModel = hiltViewModel()
    ) {
        Log.d("ResultScreenView", "ResultScreenView Build")
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(key1 = productQuery) {
            if (uiState is ResultScreenState.Loading) {
                viewModel.onInitialize(productQuery)
            }
        }

        when (uiState) {
            is ResultScreenState.Success -> ProductList().Build((uiState as ResultScreenState.Success).products) { product ->
                val json = Gson().toJson(product)
                val encode = URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
                navController.navigate(Screens.DetailsScreen.route + "/$encode")
            }

            is ResultScreenState.Loading -> LoadingState().Build()

            is ResultScreenState.UnknownError -> ErrorState().Build()

            is ResultScreenState.Empty -> EmptyState().Build()

            is ResultScreenState.NotFound -> NotFoundState().Build()

            is ResultScreenState.ServerError -> ServerErrorState().Build()

            is ResultScreenState.UnknownHttpError -> UnknownHttpState().Build()
        }
    }
}