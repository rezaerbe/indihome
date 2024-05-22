package com.erbe.feature.indihome.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.em

@Composable
fun IndihomeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkColorScheme(background = Color.Black)
        else -> lightColorScheme(background = Color.White, surface = Color.White)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = {
            ProvideTextStyle(
                value = TextStyle(
                    lineHeight = 1.5.em,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
                content = {
                    Surface(color = MaterialTheme.colorScheme.background) {
                        content()
                    }
                }
            )
        }
    )
}