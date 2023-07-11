package com.example.sure_market.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.sure_market.R
import com.example.sure_market.supports.CategoryList
import com.example.sure_market.viewmodel.PostViewModel

@Composable
fun CategoryDialog(onClick: () -> Unit, viewModel: PostViewModel) {
    Dialog(onDismissRequest = { onClick() }) {
        Surface(
            modifier = Modifier
                .width(200.dp)
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.small,
            color = Color.White
        ) {
            DialogContent(onClick, viewModel = viewModel)
        }
    }
}

@Composable
fun DialogContent(onClick: () -> Unit, viewModel: PostViewModel) {
    val list = CategoryList.getCategoryList()
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(list) { item ->
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                viewModel.setCategory(item)
                onClick()
            }) {
                Text(text = item)
            }

        }
    }
}