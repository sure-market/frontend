package com.example.sure_market.network

import android.media.Image
import android.util.Log
import com.example.sure_market.data.*
import com.google.android.gms.common.api.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response


class PostRepository {
    private val user = RetrofitImpl.getRetrofitService()

    suspend fun getPostRegister(files: List<MultipartBody.Part>, postDto: RequestBody): Flow<ApiState> = flow {

        kotlin.runCatching {
            user.postRegister(files = files, postDto = postDto)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getLoadListData(accessToken: String): Flow<ApiState> = flow {

        kotlin.runCatching {
            user.loadListData()
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure {error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getLoadDetailData(postId: Long): Flow<ApiState> = flow {
        kotlin.runCatching {
            user.loadDetailData(postId = postId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun postLike(accessToken: String, postId: Long): Flow<ApiState> = flow {
        kotlin.runCatching {
            user.postLike(postId = postId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }

}