package com.example.weather.ui.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.weather.api.WeatherApiImpl
import com.example.weather.db.CityWeather
import com.example.weather.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    val items: LiveData<List<CityWeather>> = repository.allCities.asLiveData()

    val respond = MutableLiveData<Boolean>()

    fun updateWeather() {
        viewModelScope.launch {
            if (items.value != null) {
                for (item in items.value!!) {
                    val updated = WeatherApiImpl.getWeatherByCity(item.name)!!
                    item.lat = updated.lat
                    item.lon = updated.lon
                    item.temp = updated.temp
                    repository.updateCityWeather(item)
                }
            }
        }

    }

    fun addCity(city: String) {
        viewModelScope.launch {
            val result = WeatherApiImpl.getWeatherByCity(city)
            if (result != null) {
                Log.d("tag", "result: ${result.toString()}")
                repository.insert(result)
                respond.value = true
            } else {
                respond.value = false
            }
        }
    }

    fun updateCityWeather(city: String) {
        viewModelScope.launch {
            val cityWeather = WeatherApiImpl.getWeatherByCity(city)!!
            repository.updateCityWeather(cityWeather)
        }
    }

    fun deleteCity(cityWeather: CityWeather) {
        viewModelScope.launch {
            repository.delete(cityWeather)
        }
    }
}

class WeatherViewModelFactory(private val repository: WeatherRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}