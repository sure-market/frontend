package com.example.sure_market.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sure_market.data.ApiState
import com.example.sure_market.data.ResponseDetailPostData
import com.example.sure_market.data.ResponseListData
import com.example.sure_market.network.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: PostRepository):ViewModel() {
    private val _viewRepository = MutableStateFlow<ResponseListData?>(null)
    val viewRepository: StateFlow<ResponseListData?> = _viewRepository

    fun requestViewRepository(postId: Int) {
        viewModelScope.launch {
            val postData = repository.getLoadDetailData(postId).first()
            when(postData) {
                is ApiState.Success<*> -> {
                    Log.d("daeYoung", "postDetailData load Success: ${_viewRepository.value}")
                    _viewRepository.value = postData.value as ResponseListData
                }
                ApiState.Loading -> TODO()
                is ApiState.Error -> {
                    Log.d("daeYoung", "callPostList Error: ${postData.errMsg}")
                }
            }
        }
    }

}