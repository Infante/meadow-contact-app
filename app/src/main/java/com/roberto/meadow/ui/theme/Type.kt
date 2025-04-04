package com.roberto.meadow.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    titleLarge = TextStyle( // Title 3 Bold
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 20.5.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle( // Title 3 Medium
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 20.5.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle( // Headline
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        lineHeight = 17.5.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle( // Body Medium
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 17.5.sp,
        letterSpacing = 0.sp
    )
)
