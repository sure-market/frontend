package com.example.sure_market.view.setting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryBox(icon: Int, category: String) {
    Button(modifier = Modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.surface),
        elevation = null,
        onClick = { /*TODO*/ }) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp),
            painter = painterResource(id = icon),
            contentDescription = "",
            tint = Color.Unspecified
        )
        Text(text = category, fontSize = 13.sp)

    }


}