package com.test.uwork.models

import com.google.gson.annotations.SerializedName

data class Current (
    @SerializedName("temp") val temp:Double,
    @SerializedName("humidity") val humidity :Int,
        @SerializedName("clouds") val clouds:Int

)
