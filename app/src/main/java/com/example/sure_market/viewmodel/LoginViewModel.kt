package com.example.sure_market.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.sure_market.data.ApiState
import com.example.sure_market.data.ResponseUser
import com.example.sure_market.data.SignupData
import com.example.sure_market.data.UserData
import com.example.sure_market.network.ApiRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class LoginViewModel(private val apiRepository: ApiRepository) : ViewModel() {
    private val _responseLogIn = mutableStateOf<ResponseUser?>(null)
    val responseLogIn: State<ResponseUser?> = _responseLogIn

    private val _responseSignUp = mutableStateOf<Boolean>(false)
    val responseSignUp: State<Boolean> = _responseSignUp


    suspend fun requestLogInRepository(user: UserData) {
        when (val apiState = apiRepository.getPostLogIn(user).first()) {
            is ApiState.Success<*> -> {
                _responseLogIn.value = apiState.value as ResponseUser
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "로그인 실패: ${apiState.errMsg}")
            }
            is ApiState.Loading -> {}
        }
    }

    suspend fun requestSignUpRepository(user: SignupData) {
        when (val apiState = apiRepository.getPostSignUp(user).first()) {
            is ApiState.Success<*> -> {
                _responseSignUp.value = apiState.value as Boolean
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "실패: ${apiState.errMsg}")
            }
            is ApiState.Loading -> {}
        }
    }

}