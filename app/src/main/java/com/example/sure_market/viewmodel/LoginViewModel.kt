package com.example.sure_market.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.sure_market.data.ApiState
import com.example.sure_market.data.ResponseUser
import com.example.sure_market.data.SignupData
import com.example.sure_market.data.UserData
import com.example.sure_market.repository.UserRepository
import kotlinx.coroutines.flow.first

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _responseLogIn = mutableStateOf<ResponseUser?>(null)
    val responseLogIn: State<ResponseUser?> = _responseLogIn

    private val _responseSignUp = mutableStateOf<Boolean>(false)
    val responseSignUp: State<Boolean> = _responseSignUp


    suspend fun requestLogInRepository(user: UserData) {
        when (val apiState = userRepository.getPostLogIn(user).first()) {
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
        when (val apiState = userRepository.getPostSignUp(user).first()) {
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