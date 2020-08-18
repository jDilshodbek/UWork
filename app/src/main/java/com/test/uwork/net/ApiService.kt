package com.test.uwork.net

import com.test.uwork.models.Picture
import com.test.uwork.models.WeatherResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    fun getCurrentWeather(): Single<WeatherResponse>

        @GET("list/")
        fun getPictures(@Query("page") page:Int,@Query("limit") limit:Int):Single<List<Picture>>
}