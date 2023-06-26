package com.example.sure_market.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Orange500,
    primaryVariant = Orange700,
    onPrimary = Black,
    secondary = Black,
    onSecondary = White,
    surface = Grey500,
    onSurface = White
)

private val LightColorPalette = lightColors(
    primary = Orange500,
    primaryVariant = Orange700,
    onPrimary = White,
    secondary = White,
    onSecondary = Black,
    surface = Grey200,
    onSurface = Black

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun SureMarketTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
//
//    val colors = if (MaterialTheme.colors.isLight) {
//        LightColorPalette
//    } else {
//        DarkColorPalette
//    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}