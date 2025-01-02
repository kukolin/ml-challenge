package com.anezin.melichallenge.presentation.screens

sealed class Screens(val route: String) {
    data object SearchScreen : Screens("main_screen")
    data object ResultScreen : Screens("result_screen")
    data object DetailsScreen : Screens("details_screen")
}