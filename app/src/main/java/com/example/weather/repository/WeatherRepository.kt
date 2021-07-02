package com.example.weather.repository

import androidx.annotation.WorkerThread
import com.example.weather.db.CityWeather
import com.example.weather.db.CityWeatherDao
import kotlinx.coroutines.flow.Flow

class WeatherRepository(private val cityWeatherDao: CityWeatherDao) {
    val allCities: Flow<List<CityWeather>> = cityWeatherDao.getAll()

    @WorkerThread
    suspend fun insert(cityWeather: CityWeather) {
        cityWeatherDao.insertCity(cityWeather)
    }

    @WorkerThread
    suspend fun delete(cityWeather: CityWeather) {
        cityWeatherDao.deleteCity(cityWeather)
    }

    fun getCityWeatherByName(name: String) = cityWeatherDao.getByName(name)

    @WorkerThread
    suspend fun updateCityWeather(cityWeather: CityWeather) {
        cityWeatherDao.updateCityWeather(cityWeather)
    }
}