package com.example.sure_market.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sure_market.network.ApiRepository

class LoginViewModelFactory(private val repository: ApiRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ApiRepository::class.java).newInstance(repository)
    }
}