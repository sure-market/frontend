package com.example.sure_market.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.sure_market.R
import com.example.sure_market.data.ResponseListData
import com.example.sure_market.supports.noRippleClickable
import com.example.sure_market.viewmodel.MainViewModel
import java.text.DecimalFormat


@Composable
fun GridItemCard(
    responseListData: ResponseListData,
    viewModel: MainViewModel,
    onMoveDetail: (Int) -> Unit
) {
    val price = DecimalFormat("#,###").format(responseListData.price)
    var isFavorite by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(10.dp)
            .clickable { onMoveDetail(responseListData.postId.toInt()) },
        shape = RoundedCornerShape(20.dp),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Gray)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(105.dp),
                painter = rememberAsyncImagePainter("https://picsum.photos/500/360"),
                contentDescription = "dummy data"
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = responseListData.title,
                color = Color.White,
                fontSize = 16.sp
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = responseListData.region,
                color = colorResource(id = R.color.dark_gray),
                fontSize = 13.sp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "￦ $price",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Icon(
                    imageVector = if (!isFavorite) {
                        Icons.Outlined.FavoriteBorder
                    } else {
                        Icons.Default.Favorite
                    },
                    contentDescription = "favorite",
                    tint = if (!isFavorite) {
                        Color.Black
                    } else {
                        colorResource(id = R.color.orange)
                    },
                    modifier = Modifier.noRippleClickable {
                        isFavorite = !isFavorite
                        viewModel.requestPostLike(responseListData = responseListData)
                    }
                )
            }
        }
    }
}