package com.example.sure_market.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun SettingScreen(clearUser: () -> Unit) {
    Column() {
        Button(onClick = { clearUser() }) {
            Text(text = "로그아웃")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "나의 판매내역")
        }
    }
}