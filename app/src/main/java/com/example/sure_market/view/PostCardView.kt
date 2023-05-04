package com.example.sure_market.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostCardView(image: String, pagerState: PagerState) {
        Image(
            painter = rememberAsyncImagePainter(image),
            contentDescription = "detailItem image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.1f),
            contentScale = ContentScale.Crop
        )
}