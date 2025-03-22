package com.example.simplehealthmanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class HealthViewModel : ViewModel() {
    private val _temperature = MutableStateFlow(37.0) // 体温
    private val _heartRate = MutableStateFlow(70) // 心率
    private val _systolicBloodPressure = MutableStateFlow(120) // 收缩压 (高压)
    private val _diastolicBloodPressure = MutableStateFlow(80) // 舒张压 (低压)
    private val _spo2 = MutableStateFlow(98) // 血氧饱和度
    private val _stepCount = MutableStateFlow(0) // 步数
    private val _frostbiteRisk = MutableStateFlow(0) // 冻伤风险
    private val _isTemperatureSwitchOn = MutableStateFlow(true)
    private val _isHeartRateSwitchOn = MutableStateFlow(true)
    private val _isBloodOxygenSwitchOn = MutableStateFlow(true)
    private val _isBloodPressureSwitchOn = MutableStateFlow(true)

    val temperature: StateFlow<Double> = _temperature
    val heartRate: StateFlow<Int> = _heartRate
    val systolicBloodPressure: StateFlow<Int> = _systolicBloodPressure
    val diastolicBloodPressure: StateFlow<Int> = _diastolicBloodPressure
    val spo2: StateFlow<Int> = _spo2
    val stepCount: StateFlow<Int> = _stepCount
    val frostbiteRisk: StateFlow<Int> = _frostbiteRisk
    val isTemperatureSwitchOn: StateFlow<Boolean> = _isTemperatureSwitchOn
    val isHeartRateSwitchOn: StateFlow<Boolean> = _isHeartRateSwitchOn
    val isBloodOxygenSwitchOn: StateFlow<Boolean> = _isBloodOxygenSwitchOn
    val isBloodPressureSwitchOn: StateFlow<Boolean> = _isBloodPressureSwitchOn

    init {
        startUpdatingData()
    }

    fun updateTemperatureSwitch(isOn: Boolean) {
        _isTemperatureSwitchOn.value = isOn
    }

    fun updateHeartRateSwitch(isOn: Boolean) {
        _isHeartRateSwitchOn.value = isOn
    }

    fun updateBloodOxygenSwitch(isOn: Boolean) {
        _isBloodOxygenSwitchOn.value = isOn
    }

    fun updateBloodPressureSwitch(isOn: Boolean) {
        _isBloodPressureSwitchOn.value = isOn
    }

    private fun startUpdatingData() {
        viewModelScope.launch {
            while (true) {
                _temperature.value = String.format("%.1f", Random.nextDouble(35.5, 38.5)).toDouble()
                _heartRate.value = Random.nextInt(50, 120)
                _systolicBloodPressure.value = Random.nextInt(90, 140)
                _diastolicBloodPressure.value = Random.nextInt(60, 90)
                _spo2.value = Random.nextInt(90, 100)
                _stepCount.value = Random.nextInt(1000, 5000)
                _frostbiteRisk.value = Random.nextInt(0, 5) * 20 // 模拟0%, 20%, 40%, 60%, 80%

                delay(5000) // 每5秒更新一次
            }
        }
    }

    fun getTemperatureStatus(temperature: Double): String {
        return when {
            temperature > 37.5 -> "高温"
            temperature < 36.0 -> "低温"
            else -> "正常"
        }
    }

    fun getHeartRateStatus(heartRate: Int): String {
        return if (heartRate in 60..100) "正常" else "异常"
    }

    fun getBloodOxygenStatus(spo2: Int): String {
        return if (spo2 >= 95) "正常" else "偏低"
    }

    fun getBloodPressureStatus(systolic: Int, diastolic: Int): String {
        return when {
            systolic < 90 || diastolic < 60 -> "偏低"
            systolic > 140 || diastolic > 90 -> "偏高"
            else -> "正常"
        }
    }
}