package com.test.uwork.net

import com.test.uwork.BuildConfig
import com.test.uwork.utils.Contants.BASE_URL_IMAGES
import com.test.uwork.utils.Contants.BASE_URL_WEATHER
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module (override = true){
    factory { AuthInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideOkHttpClientImages() }
    factory { provideServiceApi(get()) }
    single { provideRetrofit(get()) }
    single{provideRetrofitImages(get())}
}







fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL_WEATHER).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build()
}

fun provideRetrofitImages(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL_IMAGES).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build()
}

fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(authInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {if (BuildConfig.DEBUG) {
        level = HttpLoggingInterceptor.Level.BODY
    } else{
        level = HttpLoggingInterceptor.Level.NONE
    }  }).build()
}

fun provideOkHttpClientImages(): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {if (BuildConfig.DEBUG) {
            level = HttpLoggingInterceptor.Level.BODY
        } else{
            level = HttpLoggingInterceptor.Level.NONE
        }  }).build()
}

fun provideServiceApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)