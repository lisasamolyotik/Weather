package com.example.weather.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityWeatherData(
    @Json(name = "name") val name: String,
    @Json(name = "main") val main: MainData? = null,
    @Json(name = "cod") val cod: Int,
    @Json(name = "coord") val coord: CityData

)

@JsonClass(generateAdapter = true)
data class MainData(
    @Json(name = "temp") val temp: Double? = null
)

@JsonClass(generateAdapter = true)
data class CityData(
    @Json(name = "lon") val lon: Double,
    @Json(name = "lat") val lat: Double
)