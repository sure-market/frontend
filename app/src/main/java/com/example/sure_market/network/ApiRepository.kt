package com.example.sure_market.network

import android.util.Log
import com.example.sure_market.data.ResponseUserId
import com.example.sure_market.data.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ApiRepository {
    private val user = RetrofitImpl.getRetrofitService()

    suspend fun getPostSignUp(userData: UserData): Flow<ResponseUserId> = flow {
        try {
            val response = user.postSignUp(user = userData)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                }
            } else {
                throw Exception()
            }
        } catch (e: Exception) {
            Log.d("DaeYoung", "loginAPI fail")
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getPostSignIn(email: String, password: String): Flow<ResponseUserId> = flow {
        try {
            val response = user.postSignIn(email, password)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                }
            } else {
                throw Exception()
            }
        } catch (e: Exception) {
            Log.d("DaeYoung", "loginAPI fail")
        }
    }.flowOn(Dispatchers.IO)


}