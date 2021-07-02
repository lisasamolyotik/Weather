package com.example.weather.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "city_weather_table")
data class CityWeather(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "current_temp") var temp: Double = .0,
    @ColumnInfo(name = "lon") var lon: Double = .0,
    @ColumnInfo(name = "lat") var lat: Double = .0
)
