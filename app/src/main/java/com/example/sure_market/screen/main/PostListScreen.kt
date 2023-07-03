package com.example.sure_market.screen.main

import android.content.Context
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
import com.example.sure_market.data.UserSharedPreference
import com.example.sure_market.view.GridList
import com.example.sure_market.viewmodel.MainViewModel

@Composable
fun PostListScreen(viewModel: MainViewModel, onMoveDetail: (Int) -> Unit, context: Context) {

    LaunchedEffect(Unit) {
        val user = UserSharedPreference(context).getUserPref("userToken")
        viewModel.requestViewRepository(accessToken = user!!)
    }




    GridList(viewModel = viewModel, onMoveDetail = onMoveDetail)
}