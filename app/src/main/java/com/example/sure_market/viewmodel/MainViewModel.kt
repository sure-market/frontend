package com.example.sure_market.viewmodel

import android.app.DownloadManager.Query
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sure_market.data.PostRegisterData
import com.example.sure_market.data.ResponseListData
import com.example.sure_market.network.PostRepository
import com.example.sure_market.screen.main.BottomNavItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val repository: PostRepository = PostRepository()): ViewModel() {

    private val _viewRepository = mutableStateListOf<ResponseListData>()
    val viewRepository: List<ResponseListData> = _viewRepository

    private val _topIconState = mutableStateOf<BottomNavItem>(BottomNavItem.PostListScreen)
    val topIconState: State<BottomNavItem> = _topIconState

    private val _bottomIconState = mutableStateOf(mutableListOf<Boolean>(true, false, false))
    val bottomIconState: State<List<Boolean>> = _bottomIconState

    fun bottomIconChange(p1: Boolean = false, p2: Boolean = false, p3: Boolean = false) {
        _bottomIconState.value[0] = p1
        _bottomIconState.value[1] = p2
        _bottomIconState.value[2] = p3
    }

//    val query = MutableStateFlow("")
//    val postList = query.flatMapLatest {
//        repository.getLoadListData(it)
//    }.catch {
//        Log.d("FUCK YOU", it.toString())
//    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun requestViewRepository() {
        viewModelScope.launch {
            repository.getLoadListData().catch { error ->
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