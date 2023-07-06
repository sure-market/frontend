package com.example.sure_market.viewmodel

import android.annotation.SuppressLint
import android.icu.text.DecimalFormat
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sure_market.R
import com.example.sure_market.data.ApiState
import com.example.sure_market.data.ResponseListData
import com.example.sure_market.network.MainApplication
import com.example.sure_market.network.PostRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.List

class DetailViewModel(private val repository: PostRepository) : ViewModel() {
    private val _viewRepository = mutableStateOf<ResponseListData?>(null)
    val viewRepository: State<ResponseListData?> = _viewRepository

    fun getPrice(): String = DecimalFormat("#,###").format(viewRepository.value?.price)
    fun getRegion(): String = viewRepository.value?.region ?: "null"
    fun getTitle(): String = viewRepository.value?.title ?: "null"
    fun getCategory(): String = viewRepository.value?.category ?: "null"
    private fun getUpdateTime(): String = viewRepository.value?.updatedAt ?: "null"

    // 이미지 없을 때 서버에서 그에 맞는 이미지 받아오기
    fun getImageList(): List<String> = viewRepository.value?.image ?: listOf("https://picsum.photos/400/360")


    fun calculationTime(): String {
        val nowDateTime = Calendar.getInstance().timeInMillis //현재 시간 to millisecond
        var value = ""
        val differenceValue = nowDateTime - dateTimeToMillSec(getUpdateTime()) //현재 시간 - 비교가 될 시간
        when {
            differenceValue < 60000 -> { //59초 보다 적다면
                value = "방금 전"
            }
            differenceValue < 3600000 -> { //59분 보다 적다면
                value = TimeUnit.MILLISECONDS.toMinutes(differenceValue).toString() + "분 전"
            }
            differenceValue < 86400000 -> { //23시간 보다 적다면
                value = TimeUnit.MILLISECONDS.toHours(differenceValue).toString() + "시간 전"
            }
            differenceValue < 604800000 -> { //7일 보다 적다면
                value = TimeUnit.MILLISECONDS.toDays(differenceValue).toString() + "일 전"
            }
            differenceValue < 2419200000 -> { //3주 보다 적다면
                value = (TimeUnit.MILLISECONDS.toDays(differenceValue) / 7).toString() + "주 전"
            }
            differenceValue < 31556952000 -> { //12개월 보다 적다면
                value = (TimeUnit.MILLISECONDS.toDays(differenceValue) / 30).toString() + "개월 전"
            }
            else -> { //그 외
                value = (TimeUnit.MILLISECONDS.toDays(differenceValue) / 365).toString() + "년 전"
            }
        }
        return value
    }

    @SuppressLint("SimpleDateFormat")
    private fun dateTimeToMillSec(dateTime: String): Long {
        var timeInMilliseconds: Long = 0
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val mDate = sdf.parse(dateTime)
            timeInMilliseconds = mDate.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return timeInMilliseconds
    }

    // 게시글 상세 정보 request API
    fun requestViewRepository(postId: Int) {
        viewModelScope.launch {
            val postData = repository.getLoadDetailData(postId.toLong()).first()
            when (postData) {
                is ApiState.Success<*> -> {
                    _viewRepository.value = postData.value as ResponseListData
                }
                ApiState.Loading -> TODO()
                is ApiState.Error -> {
                    Log.d("daeYoung", "loadPostList Error: ${postData.errMsg}")
                }
            }
        }
    }

    // 찜 등록 request API
    fun requestPostLike() {
        viewModelScope.launch {
            val accessToken = MainApplication.prefs.getUserPref(
                "userToken",
                ""
            )
            when(val response = repository.postLike(accessToken = "Bearer $accessToken", postId = viewRepository.value?.postId!!).first()) {
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
}