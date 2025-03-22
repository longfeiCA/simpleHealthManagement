package com.example.simplehealthmanagement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

// UI Entrance
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthScreen(viewModel: HealthViewModel) {
    val temperature by viewModel.temperature.collectAsState()
    val heartRate by viewModel.heartRate.collectAsState()
    val systolicBloodPressure by viewModel.systolicBloodPressure.collectAsState()
    val diastolicBloodPressure by viewModel.diastolicBloodPressure.collectAsState()
    val spo2 by viewModel.spo2.collectAsState()
    val stepCount by viewModel.stepCount.collectAsState()
    val frostbiteRisk by viewModel.frostbiteRisk.collectAsState()
    val isTemperatureSwitchOn by viewModel.isTemperatureSwitchOn.collectAsState()
    val isHeartRateSwitchOn by viewModel.isHeartRateSwitchOn.collectAsState()
    val isBloodOxygenSwitchOn by viewModel.isBloodOxygenSwitchOn.collectAsState()
    val isBloodPressureSwitchOn by viewModel.isBloodPressureSwitchOn.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { /*TODO*/ },
                    icon = { Icon(Icons.Filled.BatteryFull, contentDescription = "首页") },
                    label = { Text("首页") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = { Icon(Icons.Filled.BatteryFull, contentDescription = "数据") },
                    label = { Text("数据") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = { Icon(Icons.Filled.BatteryFull, contentDescription = "个人") },
                    label = { Text("个人") }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 元素1
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray.copy(alpha = 0.3f))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("你好，用户001")
                Text("设备已连接")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("设备电量")
                    Icon(Icons.Filled.BatteryFull, contentDescription = "电池电量")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 元素2
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 组件1
                Button(
                    onClick = { /* 不可点击 */ },
                    enabled = false,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                ) {
                    Icon(Icons.Filled.Pause, contentDescription = "暂停")
                }

                // 组件2
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color.Cyan.copy(alpha = 0.6f), shape = RoundedCornerShape(4.dp))
                            .fillMaxWidth()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("运动记录：今日已走${stepCount}步", color = Color.White)
                    }
                    Box(
                        modifier = Modifier
                            .background(Color.Cyan.copy(alpha = 0.6f), shape = RoundedCornerShape(4.dp))
                            .fillMaxWidth()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("冻伤风险可能性${frostbiteRisk}%", color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 元素3
            Text(
                text = "各项指标",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 元素4
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                VitalCardWithSwitch(
                    title = "我的体温",
                    value = "$temperature ℃",
                    status = viewModel.getTemperatureStatus(temperature),
                    switchState = isTemperatureSwitchOn,
                    onSwitchChange = viewModel::updateTemperatureSwitch,
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )
                VitalCardWithSwitch(
                    title = "实时心率",
                    value = "$heartRate bpm",
                    status = viewModel.getHeartRateStatus(heartRate),
                    switchState = isHeartRateSwitchOn,
                    onSwitchChange = viewModel::updateHeartRateSwitch,
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                VitalCardWithSwitch(
                    title = "血样浓度",
                    value = "$spo2 %",
                    status = viewModel.getBloodOxygenStatus(spo2),
                    switchState = isBloodOxygenSwitchOn,
                    onSwitchChange = viewModel::updateBloodOxygenSwitch,
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )
                VitalCardWithSwitch(
                    title = "我的血压",
                    value = "${systolicBloodPressure}/${diastolicBloodPressure} mmHg",
                    status = viewModel.getBloodPressureStatus(systolicBloodPressure, diastolicBloodPressure),
                    switchState = isBloodPressureSwitchOn,
                    onSwitchChange = viewModel::updateBloodPressureSwitch,
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun VitalCardWithSwitch(
    title: String,
    value: String,
    status: String,
    switchState: Boolean,
    onSwitchChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, style = MaterialTheme.typography.bodyMedium)
                Switch(checked = switchState, onCheckedChange = onSwitchChange)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, style = MaterialTheme.typography.headlineSmall)
            Text(text = "状态: $status", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHealthScreen() {
    HealthScreen(viewModel = HealthViewModel())
}