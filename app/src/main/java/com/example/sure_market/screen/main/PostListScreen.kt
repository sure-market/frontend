package com.example.sure_market.screen.main

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.repeatOnLifecycle
import com.example.sure_market.view.GridList
import com.example.sure_market.viewmodel.MainViewModel

@Composable
fun PostListScreen(viewModel: MainViewModel, onMoveDetail: (Long) -> Unit) {

    LaunchedEffect(Unit) {
        Log.d("daeYoung", "계속 작동하는지 확인")
        viewModel.requestViewRepository("category")
    }

    GridList(viewModel = viewModel, onMoveDetail = onMoveDetail)
}