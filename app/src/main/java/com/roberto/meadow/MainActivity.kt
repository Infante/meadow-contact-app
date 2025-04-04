package com.roberto.meadow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.roberto.meadow.ui.AppNavGraph
import com.roberto.meadow.ui.theme.MeadowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeadowTheme {
                AppNavGraph()
            }
        }
    }
}
