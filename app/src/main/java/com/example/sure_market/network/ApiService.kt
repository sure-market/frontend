package com.example.sure_market.network

import com.example.sure_market.data.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("/auth/users/signup")
    suspend fun postSignUp(@Body user: SignupData): Boolean

    @POST("/auth/users/login")
    suspend fun postLogIn(@Body user: UserData): ResponseUser

    @Multipart
    @POST("/api/v1/articles/")
    suspend fun postRegister(
        @Part files: List<MultipartBody.Part>,
        @Part("postDto") postDto: RequestBody,
    ): ResponsePostId

//    @GET("/api/v1/articles/")
//    suspend fun loadListData(@Header("Authorization") Authorization: String): List<ResponseListData>

    @GET("/api/v1/articles/")
    suspend fun loadListData(): List<ResponseListData>

    @GET("/api/v1/articles/{postId}")
    suspend fun loadDetailData(@Path("postId") postId: Long): ResponseDetailPostData

    @POST("/api/v1/articles/like")
    suspend fun postLike(@Query("postId") postId: Long)
}

