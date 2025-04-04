package com.roberto.meadow.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val MeadowColorScheme = lightColorScheme(
    primary = Success600,
    onPrimary = White,

    secondary = Neutral600,
    onSecondary = White,

    background = Neutral100,
    onBackground = Black,

    surface = White,
    onSurface = Black,

    error = Danger600,
    onError = White,

    tertiary = Yellow,
    onTertiary = Black,

    outline = Neutral200
)

@Composable
fun MeadowTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MeadowColorScheme,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}
