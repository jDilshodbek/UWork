package com.test.uwork.net

import com.test.uwork.utils.Contants.APP_ID
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        val url = req.url().newBuilder()
            .addQueryParameter("APPID", APP_ID)
            .addQueryParameter("id","498817")
            .addQueryParameter("lang","ru")
            .addQueryParameter("units","metric")
            .build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}