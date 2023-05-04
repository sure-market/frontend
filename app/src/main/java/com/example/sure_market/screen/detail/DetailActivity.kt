package com.example.sure_market.screen.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModelProvider
import com.example.sure_market.data.ResponseDetailPostData
import com.example.sure_market.network.PostRepository
import com.example.sure_market.screen.main.MainActivity
import com.example.sure_market.ui.theme.SureMarketTheme
import com.example.sure_market.viewmodel.DetailViewModel
import com.example.sure_market.viewmodel.MainViewModel
import com.example.sure_market.viewmodel.MainViewModelFactory
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModelFactory: MainViewModelFactory by lazy {
            MainViewModelFactory(PostRepository())
        }
        val viewModel: DetailViewModel by lazy {
            ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]
        }
        var postId: Int

        super.onCreate(savedInstanceState)
        setContent {
            var responseDetailPostData: ResponseDetailPostData? = null
            LaunchedEffect(Unit) {
                Intent(applicationContext, MainActivity::class.java).also {
                    postId = it.getIntExtra("postId", 0)
                }
                viewModel.requestViewRepository(postId)
                viewModel.viewRepository.value?.let { responseDetailPostData = it }
            }

            SureMarketTheme {
                // responseDetailPostData?.let { DetailItemScreen() } // API 통신 에러
                DetailItemScreen()
            }
        }
    }
}