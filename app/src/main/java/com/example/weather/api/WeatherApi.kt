package com.example.weather.api

import android.util.Log
import com.example.weather.db.CityWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/data/2.5/weather?appid=5bbfba979ac8a086973f7acd7211c129&units=metric")
    suspend fun getWeatherByCity(@Query(value = "q") city: String): CityWeatherData

    @GET("/data/2.5/onecall?exclude=minutely,hourly,alert&appid=5bbfba979ac8a086973f7acd7211c129&units=metric")
    suspend fun getWeeklyForecastByCity(@Query(value = "lat") lat: String, @Query(value = "lon") lon: String): WeeklyForecastData
}

object WeatherApiImpl {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://api.openweathermap.org")
        .build()

    private val weatherApiService = retrofit.create(WeatherApi::class.java)

    suspend fun getWeatherByCity(city: String): CityWeather? {
        return try {
            val respond = withContext(Dispatchers.IO) {
                weatherApiService.getWeatherByCity(city)
            }
            CityWeather(
                respond.name,
                respond.main!!.temp!!,
                respond.coord.lon,
                respond.coord.lat
            )

        } catch (e: Exception) {
            Log.e("tag", e.localizedMessage)
            null
        }
    }

    suspend fun getDefaultCitiesWeather(cities: List<String>): MutableList<CityWeather> {
        Log.d("tag", "getDefaultCitiesWeather called")
        val result = mutableListOf<CityWeather>()
        for (city in cities) {
            getWeatherByCity(city)?.let { result.add(it) }
        }
        Log.d("tag", "result: ${result.joinToString(", ")}")
        return result
    }

    suspend fun getWeeklyForecastByCity(lat: String, lon: String): WeeklyForecastData {
        return withContext(Dispatchers.IO) {
            weatherApiService.getWeeklyForecastByCity(lat, lon)
        }
    }
}