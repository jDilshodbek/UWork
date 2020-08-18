package com.test.uwork.viewmodel

import androidx.lifecycle.ViewModel
import com.test.uwork.repository.WeatherRepository
import org.koin.dsl.module.module

val viewModelModule = module {
    factory { WeatherViewModel(get()) }
}


class WeatherViewModel(private val weatherRepo: WeatherRepository) : ViewModel() {

    val weather = weatherRepo.getWeather()

}