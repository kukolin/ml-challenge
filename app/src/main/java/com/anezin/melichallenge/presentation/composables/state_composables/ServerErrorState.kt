package com.anezin.melichallenge.presentation.composables.state_composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

class ServerErrorState {
    @Composable
    fun Build() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Estamos con problemas en nuestro servicio, intente nuevamente más tarde.",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}