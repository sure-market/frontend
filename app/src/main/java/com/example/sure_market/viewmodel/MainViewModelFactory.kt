package com.example.sure_market.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sure_market.repository.PostRepository

class MainViewModelFactory(private val repository: PostRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PostRepository::class.java).newInstance(repository)
    }
}