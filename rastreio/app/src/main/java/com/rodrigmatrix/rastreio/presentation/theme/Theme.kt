package com.rodrigmatrix.rastreio.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.rodrigmatrix.rastreio.R

private val DarkColorPalette = darkColors(
    primary = Color(R.attr.colorPrimary),
    primaryVariant = Color(R.attr.colorPrimaryVariant),
    secondary = Color(R.attr.colorSecondary),
    background = Color(R.attr.colorSurface),
    surface = Color(R.attr.colorSurface)
)

private val LightColorPalette = lightColors(
    primary = Color(R.attr.colorPrimary),
    primaryVariant = Color(R.attr.colorPrimaryVariant),
    secondary = Color(R.attr.colorSecondary),
    background = Color(R.attr.colorSurface),
    surface = Color(R.attr.colorSurface)
)

@Composable
fun RastreioTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val context = LocalContext.current
    val primaryColor = ContextCompat.getColor(context, R.color.colorPrimary)
    val primaryVariant = ContextCompat.getColor(context, R.color.colorPrimaryVariant)
    val secondary = ContextCompat.getColor(context, R.color.colorSecondary)
    val background = ContextCompat.getColor(context, R.color.colorBackground)
    val surface = ContextCompat.getColor(context, R.color.colorSurface)

    val colors = if (darkTheme) {
        darkColors(
            primary = Color(primaryColor),
            primaryVariant = Color(primaryVariant),
            secondary = Color(secondary),
            background = Color(background),
            surface = Color(surface)
        )
    } else {
        lightColors(
            primary = Color(primaryColor),
            primaryVariant = Color(primaryVariant),
            secondary = Color(secondary),
            background = Color(background),
            surface = Color(surface)
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}