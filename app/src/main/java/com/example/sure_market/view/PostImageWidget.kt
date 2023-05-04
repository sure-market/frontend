package com.example.sure_market.view

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.sure_market.R
import com.example.sure_market.viewmodel.PostViewModel

@Composable
fun PostImageWidget(uri: Uri, viewModel: PostViewModel) {

    LaunchedEffect(Unit) {
        Log.d("DaeYoung", "lazyRow: $uri")
    }

    Box(modifier = Modifier
        .height(80.dp)
        .fillMaxWidth()) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.wrapContentHeight()
        ) {

                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(
                            LocalContext.current
                        ).data(uri).size(Size.ORIGINAL).build()
                    ),
                    contentDescription = "upload image",
                    modifier = Modifier.size(70.dp),
                    contentScale = ContentScale.Crop
                )
        }
        Icon(
            painter = painterResource(id = R.drawable.baseline_close_24),
            contentDescription = "close",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 6.dp, y = (-6).dp)
                .size(15.dp)
                .clickable { viewModel.deleteUri(uri) }
        )
    }
}