package com.example.sure_market.screen.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.sure_market.network.PostRepository
import com.example.sure_market.ui.theme.SureMarketTheme
import com.example.sure_market.viewmodel.DetailViewModel
import com.example.sure_market.viewmodel.MainViewModelFactory

class DetailActivity : AppCompatActivity() {
    @SuppressLint("StateFlowValueCalledInComposition")
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
            postId = intent.getIntExtra("postId", 0)
//            LaunchedEffect(Unit) {
//            }
            viewModel.requestViewRepository(postId)
            SureMarketTheme {
                // responseDetailPostData?.let { DetailItemScreen() } // API 통신 에러
                DetailItemScreen(viewModel = viewModel)
            }
        }


    }
}
