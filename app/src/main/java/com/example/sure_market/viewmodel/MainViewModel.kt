package com.example.sure_market.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sure_market.R
import com.example.sure_market.data.ApiState
import com.example.sure_market.data.ResponseListData
import com.example.sure_market.network.MainApplication
import com.example.sure_market.repository.PostRepository
import com.example.sure_market.screen.main.BottomNavItem
import kotlinx.coroutines.flow.first
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

    // 게시글 리스트 불러오기 request API
    fun requestViewRepository(accessToken: String) {
        viewModelScope.launch {
            repository.getLoadListData(accessToken = "Bearer $accessToken").collect {
                when(it) {
                    is ApiState.Success<*> -> {
                        val list = it.value as List<ResponseListData>
                        Log.d("daeYoung", "리스트: ${list}")
                        list.filterNot {
                            it in _viewRepository
                        }.forEach {
                            _viewRepository.add(it)
                        }
                    }
                    is ApiState.Error -> {
                        Log.d("daeYoung", "callPostList Error: ${it.errMsg}")
                    }
                    is ApiState.Loading -> TODO()
                }
            }
        }
    }

    fun requestPostLike(responseListData: ResponseListData) {
        viewModelScope.launch {
            val currentPostId = responseListData.postId
            val accessToken = MainApplication.prefs.getUserPref(
                "userToken",
                ""
            )
            when(val response = repository.postLike(accessToken = "Bearer $accessToken", postId = currentPostId).first()) {
                is ApiState.Success<*> -> {
                    Log.d("daeYoung", "requestPostLike success")
                }
                ApiState.Loading -> TODO()
                is ApiState.Error -> {
                    Log.d("daeYoung", "requestPostLike fail ${response.errMsg}")
                }
            }
        }
    }
    fun setTopIconState(bottomNavItem: BottomNavItem) {
        _topIconState.value = bottomNavItem
    }

    fun getMyTransactionList() = listOf(
        "관심목록",
        "판매내역",
        "구매내역",
        "중고거래 가계부"
    )

    fun getMyTransactionIconList() = listOf(
        R.drawable.outline_favorite_border_24,
        R.drawable.outline_feed_24,
        R.drawable.outline_shopping_bag_24,
        R.drawable.outline_library_books_24
    )
}