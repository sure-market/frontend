package com.example.sure_market.data

import com.google.gson.annotations.SerializedName

data class ResponseDetailPostData(
    @SerializedName("user_id")
    val userId: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("price")
    val price: Long,

    @SerializedName("category")
    val category: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("region")
    val region: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("status")
    val status: String,
)
