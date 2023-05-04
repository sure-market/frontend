package com.example.sure_market.data

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("name")
    val name: String,
    @SerializedName("phone_num")
    val phoneNum: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
