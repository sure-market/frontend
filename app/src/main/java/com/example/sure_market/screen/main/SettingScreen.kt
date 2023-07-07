package com.example.sure_market.screen.main

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sure_market.R
import com.example.sure_market.screen.detail.DetailItemScreen
import com.example.sure_market.supports.CategoryList
import com.example.sure_market.view.CustomButton
import com.example.sure_market.view.setting.CarrotPayBox
import com.example.sure_market.view.setting.CategoryBox
import com.example.sure_market.view.setting.SubTitle
import com.example.sure_market.viewmodel.MainViewModel

@Composable
fun SettingScreen(viewModel: MainViewModel, logout: () -> Unit) {
    val categoryList = CategoryList.getCategoryList()
    val categoryIconList = CategoryList.getCategoryIconList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_person_24),
                    contentDescription = "profile image",
                    modifier = Modifier
                        .size(30.dp, 30.dp)
                        .border(
                            width = 1.dp,
                            color = contentColorFor(backgroundColor = MaterialTheme.colors.secondary),
                            shape = MaterialTheme.shapes.large
                        )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "대영몬",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold
                ) /* TODO(사용자 이름 넣기) */
            }
            CustomButton() {
                Text(
                    text = "프로필 보기",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        CarrotPayBox()

        Spacer(modifier = Modifier.height(16.dp))

        SubTitle(title = "최근 방문", modifier = Modifier.padding(start = 16.dp, end = 16.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(categoryList.size) {
                CategoryBox(icon = categoryIconList[it], category = categoryList[it])
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        SubTitle(
            title = "나의 거래",
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
        )

        viewModel.getMyTransactionList().forEachIndexed { index: Int, text: String ->
            Button(modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                elevation = null,
                onClick = { /*TODO*/ }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 8.dp),
                        painter = painterResource(id = viewModel.getMyTransactionIconList()[index]),
                        contentDescription = null
                    )
                    Text(text = text)
                }
            }
        }

        Button(onClick = { logout() }) {
            Text(text = "로그아웃")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "나의 판매내역")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    SettingScreen(viewModel = MainViewModel(), logout = {})
}