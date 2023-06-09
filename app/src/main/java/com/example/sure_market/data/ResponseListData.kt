package com.example.sure_market.data

import com.google.gson.annotations.SerializedName

data class ResponseListData(
    @SerializedName("postId")
    val postId: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("price")
    val price: Long,

    @SerializedName("image")
    val image: List<String>,

    @SerializedName("region")
    val region: String,

    @SerializedName("userId")
    val userId: String,

    @SerializedName("updatedAt")
    val updatedAt: String,
)
