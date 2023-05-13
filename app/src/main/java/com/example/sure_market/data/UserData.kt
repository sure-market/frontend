package com.example.sure_market.data

import com.google.gson.annotations.SerializedName

data class UserData(

    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String
)
