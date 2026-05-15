package dev.jefrien.neurobeat.app.theme

import androidx.compose.ui.graphics.Color

data class AppColors(
    val backgroundGradientStart: Color,
    val backgroundGradientEnd: Color,
    val surface: Color,
    val surfaceGlass: Color,
    val accent: Color,
    val accentGlow: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val divider: Color,
    val error: Color,
    val success: Color,
    val isAmoled: Boolean = false
)

fun darkAppColors(
    accent: Color = Color(0xFFE040FB),
    gradientStart: Color = Color(0xFF0A0A1A),
    gradientEnd: Color = Color(0xFF1A0A2E),
    cardOpacity: Float = 0.12f
): AppColors = AppColors(
    backgroundGradientStart = gradientStart,
    backgroundGradientEnd = gradientEnd,
    surface = Color(0xFF12121E),
    surfaceGlass = Color.White.copy(alpha = cardOpacity.coerceIn(0.05f, 0.4f)),
    accent = accent,
    accentGlow = accent.copy(alpha = 0.3f),
    textPrimary = Color(0xFFFFFFFF),
    textSecondary = Color(0xFF9E9E9E),
    textTertiary = Color(0xFF666666),
    divider = Color.White.copy(alpha = 0.08f),
    error = Color(0xFFCF6679),
    success = Color(0xFF4CAF50)
)

fun lightAppColors(
    accent: Color = Color(0xFF9C27B0),
    gradientStart: Color = Color(0xFFF3E5F5),
    gradientEnd: Color = Color(0xFFE1BEE7),
    cardOpacity: Float = 0.12f
): AppColors = AppColors(
    backgroundGradientStart = gradientStart,
    backgroundGradientEnd = gradientEnd,
    surface = Color(0xFFFFFFFF),
    surfaceGlass = Color.Black.copy(alpha = cardOpacity.coerceIn(0.05f, 0.2f)),
    accent = accent,
    accentGlow = accent.copy(alpha = 0.2f),
    textPrimary = Color(0xFF1A1A1A),
    textSecondary = Color(0xFF666666),
    textTertiary = Color(0xFF999999),
    divider = Color.Black.copy(alpha = 0.08f),
    error = Color(0xFFB00020),
    success = Color(0xFF2E7D32)
)

fun amoledAppColors(
    accent: Color = Color(0xFFE040FB),
    cardOpacity: Float = 0.08f
): AppColors = AppColors(
    backgroundGradientStart = Color.Black,
    backgroundGradientEnd = Color.Black,
    surface = Color.Black,
    surfaceGlass = Color.White.copy(alpha = cardOpacity.coerceIn(0.03f, 0.15f)),
    accent = accent,
    accentGlow = accent.copy(alpha = 0.4f),
    textPrimary = Color(0xFFFFFFFF),
    textSecondary = Color(0xFFAAAAAA),
    textTertiary = Color(0xFF666666),
    divider = Color.White.copy(alpha = 0.06f),
    error = Color(0xFFCF6679),
    success = Color(0xFF4CAF50),
    isAmoled = true
)
