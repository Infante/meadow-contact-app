package com.roberto.meadow.ui.screens

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button

@Composable
fun ContactListScreen(
    onContactClick: (String) -> Unit
) {
    Scaffold() { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text("Hello from the screen!")
        }

        Button(onClick = {
            onContactClick("1234")
        }) {
            Text("Navigate to Contact Detail Test")
        }
    }
}