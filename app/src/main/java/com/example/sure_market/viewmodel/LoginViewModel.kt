package com.example.sure_market.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sure_market.data.ResponseUserId
import com.example.sure_market.network.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(private val apiRepository: ApiRepository): ViewModel() {
    private val _viewRepository = MutableStateFlow<ResponseUserId?>(null)
    val viewRepository: StateFlow<ResponseUserId?> = _viewRepository

    fun requestViewRepository(email: String, password: String) {
        viewModelScope.launch {
            apiRepository.getPostSignIn(email, password).catch { error ->
                Log.d("deaYoung", "requestViewRepository error ${error.message}")
            }.collect { value ->
                _viewRepository.value = value
            }
        }
    }

}