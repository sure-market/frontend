package com.example.sure_market.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.sure_market.data.ResponseDetailPostData
import com.example.sure_market.network.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull

class DetailViewModel(private val repository: PostRepository):ViewModel() {
    private val _viewRepository = MutableStateFlow<ResponseDetailPostData?>(null)
    val viewRepository: StateFlow<ResponseDetailPostData?> = _viewRepository

    suspend fun requestViewRepository(postId: Int) {
        _viewRepository.value = kotlin.runCatching {
            repository.getLoadDetailData(postId).firstOrNull()
        }.getOrNull()

        kotlin.runCatching {
            repository.getLoadDetailData(postId).firstOrNull()
        }.onSuccess {response ->
            response?.let {
                _viewRepository.value = it
            } ?: Log.d("deaYoung", "ResponseDetailPostData is null")
        }.onFailure {
            Log.d("deaYoung", "loadDetailPostData fail")
        }
    }

}