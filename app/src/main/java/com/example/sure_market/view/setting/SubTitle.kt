package com.example.sure_market.view.setting

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun SubTitle(modifier: Modifier = Modifier, title: String) {
    Text(text = title, modifier = modifier, fontWeight = FontWeight.Bold)
}