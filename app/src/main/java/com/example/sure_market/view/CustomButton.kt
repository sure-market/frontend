package com.example.sure_market.view

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.sure_market.R

@Composable
fun CustomButton(composable: @Composable () -> Unit) {
    Button(modifier = Modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
        onClick = { /*TODO*/ }
    ) {
        composable()
    }
}