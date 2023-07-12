package com.example.sure_market.screen.mypostlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.sure_market.ui.theme.SureMarketTheme
import com.example.sure_market.viewmodel.DetailViewModel
import com.example.sure_market.viewmodel.MyPostListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPostListActivity : AppCompatActivity() {
    private val viewModel: MyPostListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SureMarketTheme {
                MyPostListScreen(viewModel = viewModel)
            }
        }
    }
}