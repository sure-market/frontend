package com.example.sure_market.viewmodel

import android.app.DownloadManager.Query
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sure_market.data.PostRegisterData
import com.example.sure_market.data.ResponseListData
import com.example.sure_market.network.PostRepository
import com.example.sure_market.screen.main.BottomNavItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val repository: PostRepository): ViewModel() {

    private val _viewRepository = mutableStateListOf<ResponseListData>()
    val viewRepository: List<ResponseListData> = _viewRepository

    private val _topIconState = mutableStateOf<BottomNavItem>(BottomNavItem.PostListScreen)
    val topIconState: State<BottomNavItem> = _topIconState

    val query = MutableStateFlow("")
    val postList = query.flatMapLatest {
        repository.getLoadListData(it)
    }.catch {
        Log.d("FUCK YOU", it.toString())
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun requestViewRepository(query: String) {
        viewModelScope.launch {
            repository.getLoadListData(query).catch { error ->
                Log.d("deaYoung", "requestViewRepository error ${error.message}")
            }.collect {value ->
                value.filterNot {
                    it in _viewRepository
                }.forEach {
                    _viewRepository.add(it)
                }
            }
        }
    }
    fun setTopIconState(bottomNavItem: BottomNavItem) {
        _topIconState.value = bottomNavItem
    }
}