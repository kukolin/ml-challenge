package com.anezin.melichallenge.presentation.screens.search_screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.anezin.melichallenge.presentation.screens.Screens
import kotlinx.coroutines.launch

class SearchScreenView {
    @Composable
    fun Build(
        navController: NavHostController,
        viewModel: SearchScreenViewModel = hiltViewModel(),
    ) {
        Log.d("SearchScreenView", "SearchScreenView Build")

        val coroutineScope = rememberCoroutineScope()

        val textToSearch = viewModel.textToSearch

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Ingrese su búsqueda:")
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = textToSearch, onValueChange = { viewModel.updateSearchQuery(it) })
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                onClick = {
                    coroutineScope.launch {
                        if(viewModel.canSearch) {
                            navController.navigate(Screens.ResultScreen.route + "/${textToSearch}")
                        }
                    }
                }) {
                Text(text = "Buscar", color = MaterialTheme.colorScheme.onSecondaryContainer)
            }
        }
    }
}