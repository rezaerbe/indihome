package com.erbe.feature.indihome.component

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.erbe.feature.indihome.R

val fontsFamily = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semi_bold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

private val defaultTypography = Typography()

val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(
        fontFamily = fontsFamily,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    displayMedium = defaultTypography.displayMedium.copy(
        fontFamily = fontsFamily,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    displaySmall = defaultTypography.displaySmall.copy(
        fontFamily = fontsFamily,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    headlineLarge = defaultTypography.headlineLarge.copy(
        fontFamily = fontsFamily,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    headlineMedium = defaultTypography.headlineMedium.copy(
        fontFamily = fontsFamily,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    headlineSmall = defaultTypography.headlineSmall.copy(
        fontFamily = fontsFamily,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    titleLarge = defaultTypography.titleLarge.copy(
        fontFamily = fontsFamily,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    titleMedium = defaultTypography.titleMedium.copy(
        fontFamily = fontsFamily,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    titleSmall = defaultTypography.titleSmall.copy(
        fontFamily = fontsFamily,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    bodyLarge = defaultTypography.bodyLarge.copy(
        fontFamily = fontsFamily,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    bodyMedium = defaultTypography.bodyMedium.copy(
        fontFamily = fontsFamily,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    bodySmall = defaultTypography.bodySmall.copy(
        fontFamily = fontsFamily,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    labelLarge = defaultTypography.labelLarge.copy(
        fontFamily = fontsFamily,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    labelMedium = defaultTypography.labelMedium.copy(
        fontFamily = fontsFamily,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    labelSmall = defaultTypography.labelSmall.copy(
        fontFamily = fontsFamily,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )
)