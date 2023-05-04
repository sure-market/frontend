package com.example.sure_market.data

import com.google.gson.annotations.SerializedName

data class ResponseListData(
    @SerializedName("post_id")
    val postId: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("price")
    val price: Long,

    @SerializedName("image")
    val image: String,

    @SerializedName("region")
    val region: String,

    @SerializedName("status")
    val status: String,

)
