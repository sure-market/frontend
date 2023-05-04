package com.example.sure_market.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
                tint = Color.White,
                modifier = Modifier.size(30.dp, 30.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text(text = name, color = Color.White)
                Text(text = region, color = Color.White)
            }
        }
    }
    Divider(
        thickness = 1.dp,
        modifier = Modifier.padding(16.dp),
        color = colorResource(id = R.color.soft_gray)
    )

}

