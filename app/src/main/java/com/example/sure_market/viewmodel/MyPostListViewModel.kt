package com.example.sure_market.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.sure_market.data.ApiState
import com.example.sure_market.data.PostRegisterData
import com.example.sure_market.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class MyPostListViewModel @Inject constructor(private val postRepository: PostRepository) : ViewModel() {
    private val _myPostList = mutableStateListOf<PostRegisterData>()
    val myPostList: List<PostRegisterData> = _myPostList

    suspend fun requestMyPostList() {
        when (val responses = postRepository.loadMyPostList().first()) {
            is ApiState.Success<*> -> {
                val list = responses.value as List<PostRegisterData>
                list.filterNot {
                    it in _myPostList
                }.forEach {
                    _myPostList.add(it)
                }
            }
            ApiState.Loading -> TODO()
            is ApiState.Error -> {
                Log.d("daeYoung", "loadMyPostList error: ${responses.errMsg}")
            }
        }

    }
}