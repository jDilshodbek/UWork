package com.test.uwork

import android.app.Application
import com.test.uwork.net.networkModule


import com.test.uwork.repository.forecastModule
import com.test.uwork.viewmodel.viewModelImageModule
import com.test.uwork.viewmodel.viewModelModule
import org.koin.android.ext.android.startKoin


class App : Application() {

    companion object getInstance{
        @get:Synchronized
        var instance: App? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance=this
        startKoin( this, listOf(networkModule, viewModelModule, forecastModule, viewModelImageModule))

    }
}