package com.example.sure_market.network

import com.example.sure_market.data.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("auth/users/signup")
    suspend fun postSignUp(@Body user: UserData): Response<ResponseUserId>

    @POST("auth/users/signin")
    suspend fun postSignIn(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<ResponseUserId>

    @Multipart
    @POST("/api/v1/articles")
    suspend fun postRegister(
        @Part files: List<MultipartBody.Part>,
        @Part postDto: RequestBody,
    ):Response<ResponsePostId>

    @GET("/api/v1/articles/")
    suspend fun loadListData(@Query("category")category: String):Response<List<ResponseListData>>

    @GET("/api/v1/articles/{PostId}")
    suspend fun loadDetailData(@Path("PostId") postId: Int):Response<ResponseDetailPostData>
}
