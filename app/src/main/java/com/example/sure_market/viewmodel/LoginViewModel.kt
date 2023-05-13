package com.example.sure_market.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.sure_market.data.ResponseUser
import com.example.sure_market.data.SignupData
import com.example.sure_market.data.UserData
import com.example.sure_market.network.ApiRepository
import kotlinx.coroutines.flow.firstOrNull

class LoginViewModel(private val apiRepository: ApiRepository): ViewModel() {
    private val _responseLogIn = mutableStateOf<ResponseUser?>(null)
    val responseLogIn: State<ResponseUser?> = _responseLogIn

    private val _responseSignUp = mutableStateOf<Boolean?>(false)
    val responseSignUp: State<Boolean?> = _responseSignUp

    suspend fun requestLogInRepository(user: UserData) {
        kotlin.runCatching {
            apiRepository.getPostLogIn(user).firstOrNull()
        }.onSuccess {
            _responseLogIn.value = it
        }.onFailure {
            Log.d("daeYoung", "LoginViewModel loginAPI: fail")
        }
    }

    suspend fun requestSignUpRepository(user: SignupData) {
        kotlin.runCatching {
            apiRepository.getPostSignUp(user).firstOrNull()
        }.onSuccess {
            _responseSignUp.value = it
        }.onFailure {
            Log.d("daeYoung", "LoginViewModel SignUpAPI: fail")
        }
    }

}