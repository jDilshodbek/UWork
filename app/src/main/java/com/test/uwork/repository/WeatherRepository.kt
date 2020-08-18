package com.test.uwork.repository

import com.test.uwork.net.ApiService
import org.koin.dsl.module.module

val forecastModule = module {
    factory { WeatherRepository(get()) }
}

class WeatherRepository(private val apiService: ApiService) {
    fun getWeather() = apiService.getCurrentWeather()
}