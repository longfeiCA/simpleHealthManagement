package com.example.simplehealthmanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class HealthViewModel : ViewModel() {
    // private val should start with "_"
    private val _temperature = MutableStateFlow(37.0) // 体温
    private val _heartRate = MutableStateFlow(70) // 心率
    private val _bloodPressure = MutableStateFlow(115) // 血压
    private val _spo2 = MutableStateFlow(95) // 血氧
    private val _isAlert = MutableStateFlow(false) // 是否报警

    // Similar to getters in Java. Expose these values to UI safely (UI cannot modify them)
    val temperature: StateFlow<Double> = _temperature
    val heartRate: StateFlow<Int> = _heartRate
    val bloodPressure: StateFlow<Int> = _bloodPressure
    val spo2: StateFlow<Int> = _spo2
    val isAlert: StateFlow<Boolean> = _isAlert

    init {
        startUpdatingData()
    }

    private fun startUpdatingData() {
        /*  Start a coroutine bounded to the lifecycle of ViewModel.
        This avoid unnecessary resource consumption after the user navigates away form the screen 
        and the associated Activity or Fragmnent is destroyed.  */
        viewModelScope.launch {
            while (true) {
                _temperature.value = Random.nextDouble(35.5, 38.5)
                _heartRate.value = Random.nextInt(50, 120)
                _bloodPressure.value = Random.nextInt(90, 140)
                _spo2.value = Random.nextInt(85, 100)

                _isAlert.value = _temperature.value !in 36.0..37.5 ||
                        _heartRate.value !in 60..100 ||
                        _bloodPressure.value !in 90..130 ||
                        _spo2.value < 90

                delay(5000) // 每5秒更新一次
            }
        }
    }
}
