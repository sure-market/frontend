package com.example.sure_market.screen.main

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import com.example.sure_market.view.CustomButton
import com.example.sure_market.view.setting.CarrotPayBox

@Composable
fun SettingScreen(clearUser: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
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

//        Button(onClick = { clearUser() }) {
//            Text(text = "로그아웃")
//        }
//        Button(onClick = { /*TODO*/ }) {
//            Text(text = "나의 판매내역")
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    SettingScreen({})
}