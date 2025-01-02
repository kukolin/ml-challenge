package com.anezin.melichallenge.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anezin.melichallenge.core.domain.product.Product
import com.anezin.melichallenge.presentation.screens.Screens
import com.anezin.melichallenge.presentation.screens.details_screen.DetailsScreenView
import com.anezin.melichallenge.presentation.screens.result_screen.ResultScreenView
import com.anezin.melichallenge.presentation.screens.search_screen.SearchScreenView
import com.google.gson.Gson
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SearchScreen.route) {
        composable(route = Screens.SearchScreen.route) {
            SearchScreenView().Build(navController)
        }
        composable(route = Screens.ResultScreen.route + "/{productQuery}", arguments = listOf(
            navArgument("productQuery") {
                type = NavType.StringType
                defaultValue = ""
            }
        )) { entry ->
            ResultScreenView().Build(
                entry.arguments?.getString("productQuery")!!,
                navController,
            )
        }
        composable(
            route = Screens.DetailsScreen.route + "/{product}",
            arguments = listOf(
                navArgument("product") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val jsonDecoded = URLDecoder.decode(
                entry.arguments?.getString("product"),
                StandardCharsets.UTF_8.toString(),
            )
            val product = Gson().fromJson(
                jsonDecoded, Product::class.java
            )
            product?.let {
                DetailsScreenView().Build(it)
            }
        }
    }
}
