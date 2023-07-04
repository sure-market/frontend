package com.example.sure_market.viewmodel

import android.icu.text.DecimalFormat
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sure_market.data.ApiState
import com.example.sure_market.data.ResponseListData
import com.example.sure_market.network.PostRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: PostRepository):ViewModel() {
    private val _viewRepository = mutableStateOf<ResponseListData?>(null)
    val viewRepository: State<ResponseListData?> = _viewRepository

    fun getPrice() = DecimalFormat("#,###").format(viewRepository.value?.price)


    fun requestViewRepository(postId: Int) {
        viewModelScope.launch {
            val postData = repository.getLoadDetailData(postId.toLong()).first()
            when(postData) {
                is ApiState.Success<*> -> {
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