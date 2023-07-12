package com.example.sure_market.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import com.example.sure_market.data.ApiState
import com.example.sure_market.data.ResponsePostId
import com.example.sure_market.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class PostViewModel(application: Application, private val postRepository: PostRepository) :
    AndroidViewModel(application), DefaultLifecycleObserver {

    private val _viewRepository = MutableStateFlow<ResponsePostId?>(null)
    val viewRepository: StateFlow<ResponsePostId?> = _viewRepository

    private val _title = mutableStateOf("")
    val title: State<String> = _title

    private val _category = mutableStateOf("삽니다.")
    val category: State<String> = _category

    private val _content = mutableStateOf("")
    val content: State<String> = _content

    private val _uriList = mutableStateListOf<Uri>()
    val uriList: List<Uri> = _uriList

    private val _fileList = mutableStateListOf<File>()
    val fileList: List<File> = _fileList

    private var uriListCount = mutableStateOf(0)

    private val _price = mutableStateOf<String>("")
    val price: State<String> = _price

    private val _place = mutableStateOf("장소 선택")
    val place: State<String> = _place

    private val _region = mutableStateOf("")
    val region: State<String> = _region

    suspend fun requestViewRepository(post: RequestBody) {
        val requestBodyList = mutableListOf<MultipartBody.Part>()
        fileList.forEach {
            val multipartBody = MultipartBody.Part.createFormData(
                "files",  // 서버에서 지정한 매개변수 이름
                "${it.name}",  // 실제 DB에 저장되는 이미지의 확장자 이름
                it.asRequestBody("image/*".toMediaTypeOrNull())
            )

            Log.d("daeYoung", "RequestBody: ${it.asRequestBody("image/*".toMediaTypeOrNull())}")
            Log.d("daeYoung", "MultiPart: $multipartBody")

            requestBodyList.add(multipartBody)
        }
        val multipartBodyList = requestBodyList.toList()

        when (val response =
            postRepository.getPostRegister(files = multipartBodyList, postDto = post)
                .firstOrNull()) {
            is ApiState.Success<*> -> {
                Log.d("daeYoung", "responsePostId: ${response.value}")
                _viewRepository.value = response.value as ResponsePostId
            }
            ApiState.Loading -> TODO()
            is ApiState.Error -> {
                Log.d("daeYoung", "PostRegisterFail: fail: ${response.errMsg}")
            }
            null -> TODO()
        }
    }

    fun getRealPathFromUri(uri: Uri, context: Context) {
        var proj = arrayOf(MediaStore.Images.Media.DATA)
        var cursor = context.contentResolver.query(uri, proj, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Log.d(
                    "daeYoung",
                    "데이터 확인: ${cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)}"
                )
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                Log.d("daeYoung", "데이터 확인: ${cursor.getString(columnIndex)}")
                val file = File(cursor.getString(columnIndex))
                Log.d("daeYoung", "file: ${file.name}")
                _fileList.add(file)
            }
        }
    }


    fun setTitle(title: String) {
        _title.value = title
    }

    fun setCategory(category: String) {
        _category.value = category
    }

    fun setContent(content: String) {
        _content.value = content
    }

    fun addUri(uri: Uri) {
        _uriList.add(uri)
        setUriListCount(_uriList.size)
    }

    fun deleteUri(uri: Uri) {
        _uriList.remove(uri)
        uriListCount.value -= 1
    }

    private fun setUriListCount(listCount: Int) {
        uriListCount.value = listCount
    }

    fun getUriListCount(): Int = uriListCount.value

    fun setPrice(price: String) {
        // 7자리 까지 제한
        _price.value = price.take(7)
    }

    fun setPlace(place: String) {
        _place.value = place
    }

    fun setRegion(region: String) {
        _region.value = region
    }

//    private fun String.changeRequestBody(): RequestBody =
//        this.toRequestBody("text/plain".toMediaTypeOrNull())
}