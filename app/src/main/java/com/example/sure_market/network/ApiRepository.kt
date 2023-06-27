package com.example.sure_market.network

import android.util.Log
import com.example.sure_market.data.ApiState
import com.example.sure_market.data.ResponseUser
import com.example.sure_market.data.SignupData
import com.example.sure_market.data.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.RequestBody
import java.util.Calendar

class ApiRepository {
    private val user = RetrofitImpl.getRetrofitService()

    suspend fun getPostSignUp(signupData: SignupData): Flow<ApiState> = flow {
        kotlin.runCatching {
            user.postSignUp(signupData)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getPostLogIn(userdata: UserData): Flow<ApiState> = flow {

        kotlin.runCatching {
            user.postLogIn(userdata)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)
}
