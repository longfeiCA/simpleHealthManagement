package com.example.simplehealthmanagement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState

// UI Entrance
@Composable
fun HealthScreen(viewModel: HealthViewModel) {
    // Observe data flow (collectAsState)
    val temperature by viewModel.temperature.collectAsState()
    val heartRate by viewModel.heartRate.collectAsState()
    val bloodPressure by viewModel.bloodPressure.collectAsState()
    val spo2 by viewModel.spo2.collectAsState()
    val isAlert by viewModel.isAlert.collectAsState()

    val backgroundColor = if (isAlert) Color.Red else Color.White
    val textColor = if (isAlert) Color.White else Color.Black

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 警报状态
        if (isAlert) {
            Text(
                text = "⚠️ 心率异常警报",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red, shape = RoundedCornerShape(10.dp))
                    .padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            VitalSignCard("体温", "$temperature ℃", textColor, modifier = Modifier.weight(1f))
            VitalSignCard("心率", "$heartRate bpm", if (heartRate in 60..100) textColor else Color.Red, modifier = Modifier.weight(1f))
        }

        Row {
            VitalSignCard("血压", "$bloodPressure mmHg", textColor)
            VitalSignCard("血氧", "$spo2 %", if (spo2 >= 90) textColor else Color.Red)
        }
    }
}

// A reusable card in Health Screen UI
@Composable
fun VitalSignCard(title: String, value: String, textColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(), // Replace `.weight(1f)` with `.fillMaxWidth()`
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, color = textColor, style = MaterialTheme.typography.bodyLarge)
            Text(text = value, color = textColor, style = MaterialTheme.typography.headlineMedium)
        }
    }
}

// Allow a preview of the screen in the design view in Android Studio without running the app
@Preview(showBackground = true)
@Composable
fun PreviewHealthScreen() {
    HealthScreen(viewModel = HealthViewModel())
}
