package com.example.monpasguardapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val MonpasColorScheme = lightColorScheme(
    primary = MonpasOceanBlue,
    onPrimary = MonpasWhite,

    secondary = MonpasNavy,
    onSecondary = MonpasWhite,
    secondaryContainer = MonpasSelected,
    onSecondaryContainer = MonpasNavy,

    tertiary = MonpasCoral,
    onTertiary = MonpasWhite,

    background = MonpasBackground,
    onBackground = MonpasNavy,

    surface = MonpasWhite,
    onSurface = MonpasNavy,

    surfaceVariant = MonpasIceBlue,
    onSurfaceVariant = MonpasNavy,

    outline = MonpasBorder,

    error = MonpasCoral,
    onError = MonpasWhite,
)

@Composable
fun MonpasGuardAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MonpasColorScheme,
        typography = Typography,
        content = content
    )
}