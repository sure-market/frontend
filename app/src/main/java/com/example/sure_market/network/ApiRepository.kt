package com.example.sure_market.network

import android.util.Log
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

    suspend fun getPostSignUp(signupData: SignupData): Flow<Boolean> = flow {
        kotlin.runCatching {
            user.postSignUp(signupData)
        }.onSuccess {response ->
            if (response.isSuccessful) {
                Log.d("daeYoung", "SignUpAPI Repository: ${response}")
                response.body()?.let { emit(it) }
            }
        }.onFailure {
            Log.d("DaeYoung", "SignUpAPI fail: ${it.message}")
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getPostLogIn(userdata: UserData): Flow<ResponseUser> = flow {

        kotlin.runCatching {
            user.postLogIn(userdata)
        }.onSuccess {response ->
            if (response.isSuccessful) {
                Log.d("daeYoung", "loginAPI Repository: ${response}")
                response.body()?.let { emit(it) }
            }
        }.onFailure {
            Log.d("daeYoung", "loginAPI fail: ${it.message}")
        }
    }.flowOn(Dispatchers.IO)
}

