package com.example.sure_market.screen.detail

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Share
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.sure_market.network.PostRepository
import com.example.sure_market.ui.theme.SureMarketTheme
import com.example.sure_market.viewmodel.DetailViewModel
import com.example.sure_market.viewmodel.MainViewModelFactory

class DetailActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModelFactory: MainViewModelFactory by lazy {
            MainViewModelFactory(PostRepository())
        }
        val viewModel: DetailViewModel by lazy {
            ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]
        }
        val postId: Int by lazy { intent.getIntExtra("postId", 0) }

        super.onCreate(savedInstanceState)
        setContent {
            viewModel.requestViewRepository(postId)
            SureMarketTheme {
                Scaffold(topBar = {
                    TopAppBar(title = { },
                        actions = {
                            Icon(imageVector = Icons.Outlined.Share, contentDescription = "share")
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "dropDownMenu"
                            )
                        }, navigationIcon = {
                            Icon(
                                imageVector = Icons.Outlined.KeyboardArrowLeft,
                                contentDescription = "뒤로가기"
                            )
                        },
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp
                    )
                }) { paddingValues ->
                    viewModel.viewRepository.value?.let {
                        DetailItemScreen(
                            modifier = Modifier.padding(paddingValues), viewModel = viewModel
                        )
                    } // API 통신 에러
                }
            }
        }


    }
}
