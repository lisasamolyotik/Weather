package com.example.weather.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.api.WeatherApiImpl
import com.example.weather.api.WeeklyForecastData
import kotlinx.coroutines.launch

class WeeklyForecastViewModel : ViewModel() {
    val weeklyForecastData = MutableLiveData<WeeklyForecastData>()

    fun loadForecast(lat: String, lon: String) {
        viewModelScope.launch {
            weeklyForecastData.value = WeatherApiImpl.getWeeklyForecastByCity(lat, lon)
        }
    }
}