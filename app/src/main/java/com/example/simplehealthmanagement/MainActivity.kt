package com.example.simplehealthmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
/*  Compare activities:
ComponentActivity:
    Purpose: A more modern and leaner base class for activities, introduced as part of Android Jetpack's Activity library. 
    It focuses on providing core activity functionality and integration with other Jetpack components.

What we used in Lucky-Omelet: AppCompatActivity

    Purpose: Primarily designed to provide backward compatibility for newer Android features on older devices. 
    It allows you to use features like the Action Bar (now Toolbar), Material Design themes, 
    and fragment support even on devices running older versions of Android.
    Traditionally, AppCompatActivity was the go-to base class for most Android applications using the traditional Android UI toolkit (Views).
*/
class MainActivity : ComponentActivity() {
    // variableName: Type for savedInstanceState: Bundle?
    // ? means it may be null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: HealthViewModel = viewModel() // parameter required for a Composable function 
            HealthScreen(viewModel) // Composable function
        }
    }
}