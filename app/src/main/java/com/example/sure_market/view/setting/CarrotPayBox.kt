package com.example.sure_market.view.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sure_market.R

@Composable
fun CarrotPayBox() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(MaterialTheme.shapes.medium)
            .border(width = 1.dp, color = MaterialTheme.colors.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.height(30.dp),
                    painter = painterResource(id = R.drawable.carrot_pay),
                    contentDescription = "carrot pay"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "당근하는 새로운 방법, 당근페이!", fontSize = 13.sp)
            }
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            PayButton(
                Modifier
                    .weight(1.0f)
                    .padding(end = 16.dp),
                icon = R.drawable.baseline_add_24,
                text = "충전"
            )
            PayButton(Modifier.weight(1.0f), icon = R.drawable.money_icon, text = "계좌송금")
        }
    }
}

@Composable
fun PayButton(modifier: Modifier = Modifier, icon: Int, text: String) {
    Button(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 4.dp),
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp),
            painter = painterResource(id = icon),
            contentDescription = null
        )
        Text(text = text, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CarrotPayBox()
}