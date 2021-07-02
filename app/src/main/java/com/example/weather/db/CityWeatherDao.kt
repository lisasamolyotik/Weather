package com.example.weather.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CityWeatherDao {
    @Query("select * from city_weather_table")
    fun getAll(): Flow<List<CityWeather>>

    @Query("select * from city_weather_table where name = :name")
    fun getByName(name: String): CityWeather

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(cityWeather: CityWeather)

    @Delete
    suspend fun deleteCity(cityWeather: CityWeather)

    @Update
    suspend fun updateCityWeather(cityWeather: CityWeather)
}