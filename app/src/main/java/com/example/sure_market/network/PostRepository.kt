package com.example.sure_market.network

import android.media.Image
import android.util.Log
import com.example.sure_market.data.PostRegisterData
import com.example.sure_market.data.ResponseDetailPostData
import com.example.sure_market.data.ResponseListData
import com.example.sure_market.data.ResponsePostId
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

    suspend fun getPostRegister(files: List<MultipartBody.Part>, postDto: RequestBody): Flow<ResponsePostId> = flow {

        kotlin.runCatching {
            user.postRegister(files = files, postDto = postDto)
        }.onSuccess {response ->
            if (response.isSuccessful) {
                response.body()?.let { emit(it) }
            }
        }.onFailure {
            Log.d("DaeYoung", "getPostRegisterAPI fail")
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getLoadListData(): Flow<List<ResponseListData>> = flow {

        kotlin.runCatching {
            user.loadListData()
        }.onSuccess {response ->
            if (response.isSuccessful) {
                response.body()?.let { emit(it) }
            }
        }.onFailure {
            Log.d("DaeYoung", "getLoadListDataAPI fail")
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getLoadDetailData(postId: Int): Flow<ResponseDetailPostData> = flow<ResponseDetailPostData> {
        kotlin.runCatching {
            user.loadDetailData(postId = postId)
        }.onSuccess {response ->
            if (response.isSuccessful) {
                response.body()?.let { emit(it) }
            }
        }.onFailure {
            Log.d("DaeYoung", "getLoadDetailDataAPI fail")
        }
    }.flowOn(Dispatchers.IO)

}