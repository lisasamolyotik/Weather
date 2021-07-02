package com.example.weather

import android.app.Application
import com.example.weather.db.CityWeatherDatabase
import com.example.weather.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class App : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { CityWeatherDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { WeatherRepository(database.cityWeatherDao()) }
}