package com.example.weather.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeeklyForecastData(
    @Json(name="daily") val days: List<DailyWeather>
)

@JsonClass(generateAdapter = true)
data class DailyWeather(
    @Json(name = "dt") val date: Long,
    @Json(name = "temp") val tempData: TempData
)

@JsonClass(generateAdapter = true)
data class TempData(
    @Json(name = "day") val day: Double,
    @Json(name = "night") val night: Double
)