package com.example.simplehealthmanagement

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.Battery4Bar
import androidx.compose.material.icons.filled.DataThresholding
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

// UI base component
@Composable
fun HealthScreen(viewModel: HealthViewModel) {
    // Extract state variables from the ViewModel
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
    val temperatureStatus = viewModel.getTemperatureStatus(temperature)
    val heartRateStatus = viewModel.getHeartRateStatus(heartRate)
    val bloodOxygenStatus = viewModel.getBloodOxygenStatus(spo2)
    val bloodPressureStatus = viewModel.getBloodPressureStatus(systolicBloodPressure, diastolicBloodPressure)
    val vitalStatuses = listOf(
        "体温" to temperatureStatus,
        "心率" to heartRateStatus,
        "血氧浓度" to bloodOxygenStatus,
        "血压" to bloodPressureStatus
    )
    val abnormalStatuses = vitalStatuses.filter { it.second != "正常" }
    val hasAbnormalStatus = abnormalStatuses.isNotEmpty()

    // UI parameters
    val navigationItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.Black,
        selectedTextColor = Color.Black,
        unselectedIconColor = Color.White.copy(alpha = 0.3f),
        unselectedTextColor = Color.Black.copy(alpha = 0.3f)
    )
    val scrollState = rememberScrollState()

    // Start of UI structure 用户界面结构开始
    Scaffold(
        // Navigation bar 导航栏
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
        // Whole Screen (except nav bar) 除了导航栏整个屏幕
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (hasAbnormalStatus) { // 判断是否有异常情况
                // Show alert card 展示警告卡片
                AlertCard(abnormalStatuses = abnormalStatuses)
            } else {
                // Show greetings and device status 问候用户并展示设备状态
                Row( // Greetings 问候用户
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.3f))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("您好，⚫用户001", style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold))
                }
                Row( // Device status 设备状态
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
                    ) {
                        Text("设备电量", style = TextStyle(fontSize = 15.sp))
                        Icon(Icons.Filled.Battery4Bar, contentDescription = "电池电量")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            // Button & Exercise View 按钮&运动记录
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                // Pause Button 暂停键
                IconButton(
                    onClick = { /* 暂停设备？ */ },
                    enabled = false,
                    modifier = Modifier
                        .weight(1f)
                        .size(130.dp)
                        .padding(end = 0.dp)
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
                            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 12.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon( // Running Icon 运动图标
                                Icons.AutoMirrored.Filled.DirectionsRun,
                                contentDescription = "运动",
                                modifier = Modifier.size(40.dp),
                                tint = Color.White
                            )
                            Column( // Exercise texts 运动记录文字内容
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
                            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 12.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon( // Frostbite Icon 冻伤标志
                                Icons.Outlined.AcUnit,
                                contentDescription = "冻伤",
                                modifier = Modifier.size(40.dp),
                                tint = Color.White
                            )
                            Column( // Frostbite risk texts 冻伤风险文字
                                horizontalAlignment = Alignment.End
                            ) {
                                Text("冻伤风险可能性", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text("${frostbiteRisk}%", color = Color.White)
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(0.dp))
            // 各项指标 Text
            Text(
                text = "各项指标",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Vital Cards 健康指标卡片
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Temperature Card 体温卡片
                VitalCardWithSwitch(
                    title = "我的体温",
                    value = temperature.toString(),
                    unit = "℃",
                    status = temperatureStatus,
                    switchState = isTemperatureSwitchOn,
                    onSwitchChange = viewModel::updateTemperatureSwitch,
                    backgroundPainter = painterResource(R.drawable.centigrade),
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )
                // Heart Rate Card 心率卡片
                VitalCardWithSwitch(
                    title = "实时心率",
                    value = heartRate.toString(), // Pass only the numeric value
                    unit = "bpm", // Pass the unit separately
                    status = heartRateStatus,
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
                // Blood Oxygen Card 血氧卡片
                VitalCardWithSwitch(
                    title = "血氧浓度",
                    value = spo2.toString(), // Pass only the numeric value
                    unit = "SPO₂", // Pass the unit separately
                    status = bloodOxygenStatus,
                    switchState = isBloodOxygenSwitchOn,
                    onSwitchChange = viewModel::updateBloodOxygenSwitch,
                    backgroundPainter = painterResource(R.drawable.blood_oxygen),
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )
                // Blood Pressure Card 血压卡片
                VitalCardWithSwitch(
                    title = "我的血压",
                    value = "${systolicBloodPressure}/${diastolicBloodPressure}", // Pass combined value
                    unit = "mmHg", // Pass the unit separately
                    status = bloodPressureStatus,
                    switchState = isBloodPressureSwitchOn,
                    onSwitchChange = viewModel::updateBloodPressureSwitch,
                    backgroundPainter = painterResource(R.drawable.blood_pressure),
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                )
            }
        }
    }
}

// Alert Card 警告卡片
@Composable
fun AlertCard(abnormalStatuses: List<Pair<String, String>>) {
    val isSingleAbnormal = abnormalStatuses.size == 1
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Red)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row( // Display icon and title 展示警告标志和标题
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.Warning,
                    contentDescription = "警告",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isSingleAbnormal) "${abnormalStatuses.first().first}异常警告" else "多项异常警告",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text( // Display alert message 展示警告信息
                text = if (isSingleAbnormal) {
                    val title = abnormalStatuses.first().first
                    val status = abnormalStatuses.first().second
                    when (status) {
                        "偏高" -> "目前您的${title}已达到您设置的上限"
                        "偏低" -> "目前您的${title}已低于您设置的下限"
                        else -> "目前您的${title}异常" // Fallback
                    }
                } else {
                    "目前多个指标已达到您设置的上限"
                },
                color = Color.White,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

// Vital Card 健康指标卡片
@Composable
fun VitalCardWithSwitch(
    title: String,
    value: String,
    unit: String,
    status: String,
    switchState: Boolean,
    onSwitchChange: (Boolean) -> Unit,
    backgroundPainter: Painter?, // Background image
    modifier: Modifier = Modifier
) {
    // Switch UI parameters 开关UI参数
    val switchColors = SwitchDefaults.colors(
        checkedThumbColor = Color.White,
        uncheckedThumbColor = Color.White,
        checkedTrackColor = Color(0xFF64A7E8),
        uncheckedTrackColor = Color(0xFF64A7E8),
        checkedBorderColor = Color.Transparent,
        uncheckedBorderColor = Color.Transparent,
    )
    // Card UI parameters 卡片UI参数
    val cardBackgroundColor = if (status == "正常") Color.Transparent else Color.Red.copy(alpha = 0.8f)
    val statusDisplay = if (status == "正常") "正常" else "异常: $status"
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor)
    ) {
        Box(modifier = Modifier.fillMaxSize()) { // Box supports overlap，Box支持重叠展示
            // Draw background image 添加背景图片
            backgroundPainter?.let {
                Image(
                    painter = it,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.9f)
                        .alpha(if (status == "正常") 0.2f else 0.1f) // 调整异常状态下的透明度
                        .align(Alignment.CenterStart)
                )
            }
            // Vital card content 健康指标卡片内容
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row( // Switch and More 展示开关和更多
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
                    // Switch to an transparent button to support detailed data in the future
                    // 更改为透明的按钮可以在未来支持详细数据
                    Text(
                        text = "更多",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (status == "正常") Color.Black else Color.White
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                // Determine which layout by the title
                // 根据标题判断需要什么布局
                when (title) {
                    "我的体温", "实时心率", "血氧浓度" -> {
                        Column( // Temperature+status 体温+正常
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = title,
                                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                                color = if (status == "正常") Color.Black else Color.White,
                                textAlign = TextAlign.End
                            )
                            Text(
                                text = statusDisplay,
                                style = TextStyle(fontSize = 14.sp),
                                color = if (status == "正常") Color.Gray else Color.White,
                                textAlign = TextAlign.End
                            )
                        }
                        Box( // Value 展示值
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text( // Number 数值
                                    text = value,
                                    style = TextStyle(fontSize = 36.sp, fontWeight = FontWeight.Bold),
                                    color = if (status == "正常") Color.Black else Color.White
                                )
                                Text( // Unit 单位
                                    text = unit,
                                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                                    color = if (status == "正常") Color.Black else Color.White,
                                    modifier = Modifier.padding(bottom = 2.dp)
                                )
                            }
                        }
                    }
                    else -> { // 我的血压
                        Column( // 我的血压 正常
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Text( // 我的血压
                                text = title,
                                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                                color = if (status == "正常") Color.Black else Color.White,
                                textAlign = TextAlign.End
                            )
                            Text( // 正常
                                text = statusDisplay,
                                style = TextStyle(fontSize = 14.sp),
                                color = if (status == "正常") Color.Gray else Color.White,
                                textAlign = TextAlign.End
                            )
                        }

                        Column( //高低压展示
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            verticalArrangement = Arrangement.Center
                        ) {
                            val (high, low) = value.split("/")
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(// 高压值文字
                                    text = "高压值",
                                    style = TextStyle(fontSize = 10.sp),
                                    color = if (status == "正常") Color.Black else Color.White,
                                )
                                Row(verticalAlignment = Alignment.Bottom) {
                                    Text(
                                        text = high,
                                        style = TextStyle(fontSize = 36.sp, fontWeight = FontWeight.Bold),
                                        color = if (status == "正常") Color.Black else Color.White
                                    )
                                    Text(
                                        text = unit,
                                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                                        color = if (status == "正常") Color.Black else Color.White,
                                        modifier = Modifier.padding(bottom = 2.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp)) // 增加一些垂直间距
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(// 低压值文字
                                    text = "低压值",
                                    style = TextStyle(fontSize = 10.sp),
                                    color = if (status == "正常") Color.Black else Color.White,
                                )
                                Row(verticalAlignment = Alignment.Bottom) {
                                    Text(
                                        text = low,
                                        style = TextStyle(fontSize = 36.sp, fontWeight = FontWeight.Bold),
                                        color = if (status == "正常") Color.Black else Color.White
                                    )
                                    Text(
                                        text = unit,
                                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                                        color = if (status == "正常") Color.Black else Color.White,
                                        modifier = Modifier.padding(bottom = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Preview 动态展示
@Preview(showBackground = true)
@Composable
fun PreviewHealthScreen() {
    HealthScreen(viewModel = HealthViewModel())
}