package com.example.sure_market.repository

import com.example.sure_market.data.ApiState
import com.example.sure_market.data.SignupData
import com.example.sure_market.data.UserData
import com.example.sure_market.network.RetrofitImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository {
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
