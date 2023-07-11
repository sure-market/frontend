package com.example.sure_market.data

import com.google.gson.annotations.SerializedName

data class ResponseDetailPostData(
    @SerializedName("postId")
    val postId: Long,

    @SerializedName("userId")
    val userId: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("price")
    val price: Long,

    @SerializedName("category")
    val category: String,

    @SerializedName("image")
    val image: List<String>,

    @SerializedName("region")
    val region: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("updatedAt")
    val updatedAt: String,
)
