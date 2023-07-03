package com.example.sure_market.screen.detail

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sure_market.R
import com.example.sure_market.view.DetailItemContent
import com.example.sure_market.view.DetailItemProfile
import com.example.sure_market.view.PostCardView
import com.example.sure_market.viewmodel.DetailViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailItemScreen(viewModel: DetailViewModel) {
    val scrollState = rememberScrollState()
    var isFavorite by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState()
    val testImageList = remember { emptyList<String>().toMutableList() }
    for (i in 1..10) {
        testImageList.add("https://picsum.photos/400/360")
//        Log.d("daeYoung", "pagerImage $i")
    }

//    val interactionSource = remember { MutableInteractionSource() }
//    val ripple = rememberRipple(color = Color.Blue)

    LaunchedEffect(Unit) {
//        testImageList.clear()

    }
    Scaffold(bottomBar = {
        BottomAppBar(
            modifier = Modifier
                .fillMaxWidth(),
            contentColor = contentColorFor(MaterialTheme.colors.secondary),
            backgroundColor = MaterialTheme.colors.secondary,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Icon(
                imageVector = if (isFavorite) {
                    Icons.Default.FavoriteBorder
                } else {
                    Icons.Default.Favorite
                },
                contentDescription = "favorite",
                tint = if (isFavorite) {
                    contentColorFor(MaterialTheme.colors.secondary)
                } else {
                    colorResource(id = R.color.orange)
                },
                modifier = Modifier.clickable { isFavorite = !isFavorite }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp), thickness = 1.dp,
                color = colorResource(id = R.color.soft_gray)
            )

            Spacer(modifier = Modifier.width(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "430,000원",
                    fontWeight = FontWeight.Bold,
                    color = contentColorFor(MaterialTheme.colors.secondary)
                )

                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(8.dp),
                    onClick = { /*TODO*/ }) {
                    Text(text = "채팅하기", fontWeight = FontWeight.Bold)
                }
            }

        }

    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(colorResource(id = R.color.main_color))
                .padding(it)

        ) {
            Box(modifier = Modifier) {
                HorizontalPager(count = testImageList.size, state = pagerState) { pagerIndex ->
                    PostCardView(image = testImageList[pagerIndex], pagerState = pagerState)
                }
                HorizontalPagerIndicator(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.BottomCenter),
                    pagerState = pagerState,
                )
            }



            DetailItemProfile(name = "사용자 닉네임", region = "정왕")

            DetailItemContent(
                title = "제목",
                category = "남성패션/잡화",
                time = "2시간전 ",
                content = "내용\n내용\n내용\n내용"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
//    DetailItemScreen(viewModel = DetailViewModel)
}