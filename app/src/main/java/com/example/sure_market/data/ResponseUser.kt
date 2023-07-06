package com.example.sure_market.data

import com.google.gson.annotations.SerializedName

data class ResponseUser(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
)
