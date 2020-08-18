package com.test.uwork.models

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("current") val current: Current
)

data class Main(
    @SerializedName("temp") val temp:Double,
    @SerializedName("humidity") val humidity:Int

)

data class WeatherResponse(
    @SerializedName("weather") val weather:List<Weather>,
    @SerializedName("main") val main:Main,
    @SerializedName("clouds") val clouds: Clouds,
    @SerializedName("name") val country:String
)

data class Clouds(
    @SerializedName("all") val all:Int
)




