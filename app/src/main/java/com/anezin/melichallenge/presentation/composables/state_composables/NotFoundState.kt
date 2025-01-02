package com.anezin.melichallenge.presentation.composables.state_composables

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class NotFoundState {
    @Composable
    fun Build() {
        val context = LocalContext.current
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Parece que esta página no existe :(",
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = {
                    Toast.makeText(
                        context,
                        "Error reportado. Muchas gracias! :)",
                        Toast.LENGTH_LONG
                    ).show()
                }) {
                    Text(
                        text = "Reportar error",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}