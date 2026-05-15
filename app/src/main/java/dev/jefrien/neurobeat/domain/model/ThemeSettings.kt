package dev.jefrien.neurobeat.domain.model

import androidx.compose.ui.graphics.Color

enum class ThemeMode {
    DARK, LIGHT, AMOLED
}

data class ThemeSettings(
    val themeMode: ThemeMode = ThemeMode.DARK,
    val accentColor: Color = Color(0xFFE040FB),
    val gradientStart: Color = Color(0xFF0A0A1A),
    val gradientEnd: Color = Color(0xFF1A0A2E),
    val cardOpacity: Float = 0.12f,
    val useDynamicGradient: Boolean = true
)
