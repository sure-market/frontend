package com.example.sure_market.data

import com.google.gson.annotations.SerializedName

data class ResponseUser(
    @SerializedName("id")
    val id: Long,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("roles")
    val roles: List<Roles>,
    @SerializedName("token")
    val token: String,
)
