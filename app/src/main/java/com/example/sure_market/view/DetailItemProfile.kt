package com.example.sure_market.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sure_market.R

@Composable
fun DetailItemProfile(name: String, region: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_person_24),
                contentDescription = "profile image",
                tint = contentColorFor(MaterialTheme.colors.background),
                modifier = Modifier
                    .size(30.dp, 30.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(contentColorFor(MaterialTheme.colors.background).copy(alpha = 0.3f))
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = name,
                    color = contentColorFor(MaterialTheme.colors.background),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = region,
                    color = contentColorFor(MaterialTheme.colors.background),
                    fontSize = 12.sp
                )
            }
        }
    }
    Divider(
        thickness = 1.dp,
        modifier = Modifier.padding(16.dp),
        color = colorResource(id = R.color.soft_gray)
    )

}

