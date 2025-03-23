package com.example.simplehealthmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: HealthViewModel = viewModel() // parameter required for a Composable function 
            HealthScreen(viewModel) // Composable function
        }
    }
}