package com.test.uwork.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Picture(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("author")
    @Expose
    val author: String,
    @SerializedName("download_url")
    @Expose
    val photo: String
)