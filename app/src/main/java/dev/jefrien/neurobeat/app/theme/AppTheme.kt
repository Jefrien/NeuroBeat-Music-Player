package dev.jefrien.neurobeat.app.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalAppColors = staticCompositionLocalOf { darkAppColors() }

@Composable
fun NeurobeatTheme(
    themeManager: ThemeManager,
    content: @Composable () -> Unit
) {
    val appColors = themeManager.currentColors().value
    val isDark = appColors.isAmoled || appColors.backgroundGradientStart != Color(0xFFF3E5F5)

    val colorScheme = if (isDark) {
        darkColorScheme(
            primary = appColors.accent,
            secondary = appColors.accent.copy(alpha = 0.7f),
            background = appColors.backgroundGradientStart,
            surface = appColors.surface,
            onBackground = appColors.textPrimary,
            onSurface = appColors.textPrimary
        )
    } else {
        lightColorScheme(
            primary = appColors.accent,
            secondary = appColors.accent.copy(alpha = 0.7f),
            background = appColors.backgroundGradientStart,
            surface = appColors.surface,
            onBackground = appColors.textPrimary,
            onSurface = appColors.textPrimary
        )
    }

    CompositionLocalProvider(LocalAppColors provides appColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
