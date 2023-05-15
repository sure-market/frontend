package com.example.sure_market.screen.main

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sure_market.R
import com.example.sure_market.screen.detail.DetailItemScreen
import com.example.sure_market.view.CustomButton

@Composable
fun SettingScreen(clearUser: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_person_24),
                    contentDescription = "profile image",
//                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp, 30.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colors.onPrimary,
                            shape = MaterialTheme.shapes.large
                        )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "대영몬")
            }
            CustomButton() { Text(text = "프로필 보기") }
        }
        Button(onClick = { clearUser() }) {
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
    SettingScreen({})
}