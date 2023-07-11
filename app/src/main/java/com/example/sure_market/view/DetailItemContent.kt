package com.example.sure_market.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sure_market.R

@Composable
fun DetailItemContent(title: String, category: String, time: String, content: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 16.dp)
    ) {
        Text(
            text = title,
            color = contentColorFor(MaterialTheme.colors.background),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier) {
            Text(
                text = category,
                color = contentColorFor(MaterialTheme.colors.background),
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = time,
                color = contentColorFor(MaterialTheme.colors.background),
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = content, color = contentColorFor(MaterialTheme.colors.background))

        Spacer(modifier = Modifier.height(16.dp))

        Divider(
            thickness = 1.dp,
            color = colorResource(id = R.color.soft_gray)
        )


    }

}