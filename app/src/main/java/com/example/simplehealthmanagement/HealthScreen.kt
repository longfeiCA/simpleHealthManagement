package com.example.simplehealthmanagement

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.Battery4Bar
import androidx.compose.material.icons.filled.DataThresholding
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.outlined.AcUnit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

// UI Entrance

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


    // UI parameters
    val navigationItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.Black,
        selectedTextColor = Color.Black,
        unselectedIconColor = Color.White.copy(alpha = 0.3f),
        unselectedTextColor = Color.Black.copy(alpha = 0.3f)
    )


    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF64A7E8)) {
                NavigationBarItem(
                    selected = true,
                    onClick = { /*TODO*/ },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "首页") },
                    label = { Text("首页") },
                    colors = navigationItemColors
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = { Icon(Icons.Filled.DataThresholding, contentDescription = "数据") },
                    label = { Text("数据") },
                    colors = navigationItemColors

                )
                NavigationBarItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = { Icon(Icons.Filled.Person2, contentDescription = "个人") },
                    label = { Text("个人") },
                    colors = navigationItemColors
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
            // Greetings
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.3f))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("您好，⚫用户001", style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold))
            }

            // Device status
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.3f))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text("设备已连接", style = TextStyle(fontSize = 15.sp))
                Spacer(modifier = Modifier.width(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text("设备电量", style = TextStyle(fontSize = 15.sp))
                    Icon(Icons.Filled.Battery4Bar, contentDescription = "电池电量")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Button & Exercise View
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                // Pause Button
                IconButton(
                    onClick = { /* 暂停设备？ */ },
                    enabled = false,
                    modifier = Modifier
                        .weight(1f)
                        .size(130.dp)
                        .padding(end = 1.dp)
                    // colors =

                ) {
                    Icon(
                        Icons.Filled.PauseCircle,
                        contentDescription = "暂停",
                        modifier = Modifier.size(130.dp),
                        tint = Color(0xFF64A7E8),
                    )
                }

                // Exercise and Frostbite 运动记录和冻伤风险
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Exercise records 运动记录
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF64A7E8), shape = RoundedCornerShape(10.dp))
                            .fillMaxWidth()
                            .padding(12.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.DirectionsRun,
                                contentDescription = "运动",
                                modifier = Modifier.size(40.dp),
                                tint = Color.White
                            )
                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Text("运动记录", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text("今日已走${stepCount}步", color = Color.White)
                            }
                        }
                    }


                    Spacer(modifier = Modifier.height(10.dp))


                    // Frostbite risk 冻伤风险
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF64A7E8), shape = RoundedCornerShape(10.dp))
                            .fillMaxWidth()
                            .padding(12.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                Icons.Outlined.AcUnit,
                                contentDescription = "冻伤",
                                modifier = Modifier.size(40.dp),
                                tint = Color.White
                            )
                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Text("冻伤风险可能性", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text("${frostbiteRisk}%", color = Color.White)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 各项指标 Text
            Text(
                text = "各项指标",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp, fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Vital Cards
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
                    backgroundPainter = painterResource(R.drawable.centigrade),
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )
                VitalCardWithSwitch(
                    title = "实时心率",
                    value = "$heartRate bpm",
                    status = viewModel.getHeartRateStatus(heartRate),
                    switchState = isHeartRateSwitchOn,
                    onSwitchChange = viewModel::updateHeartRateSwitch,
                    backgroundPainter = painterResource(R.drawable.heart),
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
                    backgroundPainter = painterResource(R.drawable.blood_oxygen),
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )
                VitalCardWithSwitch(
                    title = "我的血压",
                    value = "${systolicBloodPressure}/${diastolicBloodPressure} mmHg",
                    status = viewModel.getBloodPressureStatus(systolicBloodPressure, diastolicBloodPressure),
                    switchState = isBloodPressureSwitchOn,
                    onSwitchChange = viewModel::updateBloodPressureSwitch,
                    backgroundPainter = painterResource(R.drawable.blood_pressure),
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
    backgroundPainter: Painter?, // 添加背景图片选项
    modifier: Modifier = Modifier
) {
    val switchColors = SwitchDefaults.colors(
        checkedThumbColor = Color.White,
        uncheckedThumbColor = Color.White,
        checkedTrackColor = Color(0xFF64A7E8),
        uncheckedTrackColor = Color(0xFF64A7E8),
        checkedBorderColor = Color.Transparent,
        uncheckedBorderColor = Color.Transparent,
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(210.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent) // 透明背景
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Draw background image
            backgroundPainter?.let {
                Image(
                    painter = it,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.9f)
                        .alpha(0.2f)
                        .align(Alignment.CenterStart)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Switch(
                        checked = switchState,
                        onCheckedChange = onSwitchChange,
                        modifier = Modifier.scale(1f),
                        colors = switchColors
                    )
                    Text(text = "更多", style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = title, style = MaterialTheme.typography.bodyMedium)
                Text(text = value, style = MaterialTheme.typography.headlineSmall)
                Text(text = "状态: $status", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHealthScreen() {
    HealthScreen(viewModel = HealthViewModel())
}