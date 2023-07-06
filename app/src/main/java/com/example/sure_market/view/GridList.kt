package com.example.sure_market.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sure_market.viewmodel.MainViewModel

@Composable
fun GridList(viewModel: MainViewModel, onMoveDetail: (Int) -> Unit) {
    val scrollState = rememberLazyGridState()
    LazyVerticalGrid(
        state = scrollState,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
    ) {
        items(viewModel.viewRepository) {
            GridItemCard(responseListData = it, viewModel = viewModel, onMoveDetail = onMoveDetail)
        }
    }
}