package com.example.sure_market.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sure_market.network.PostRepository

class PostViewModelFactory(private val repository: PostRepository, private val application: Application):
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(application, repository) as T
        }
        return super.create(modelClass)
    }
}