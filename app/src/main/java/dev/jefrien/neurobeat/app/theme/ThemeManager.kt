package dev.jefrien.neurobeat.app.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import dev.jefrien.neurobeat.domain.model.ThemeMode
import dev.jefrien.neurobeat.domain.model.ThemeSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeManager @Inject constructor() {

    private val _themeSettings = MutableStateFlow(ThemeSettings())
    val themeSettings: StateFlow<ThemeSettings> = _themeSettings.asStateFlow()

    fun updateThemeMode(mode: ThemeMode) {
        _themeSettings.update { it.copy(themeMode = mode) }
    }

    fun updateAccentColor(color: Color) {
        _themeSettings.update { it.copy(accentColor = color) }
    }

    fun updateGradient(start: Color, end: Color) {
        _themeSettings.update { it.copy(gradientStart = start, gradientEnd = end) }
    }

    fun updateCardOpacity(opacity: Float) {
        _themeSettings.update { it.copy(cardOpacity = opacity.coerceIn(0.03f, 0.4f)) }
    }

    fun toggleDynamicGradient(enabled: Boolean) {
        _themeSettings.update { it.copy(useDynamicGradient = enabled) }
    }

    @Composable
    fun currentColors(): State<AppColors> {
        val settings = themeSettings.collectAsState()
        return androidx.compose.runtime.produceState(
            initialValue = darkAppColors(),
            key1 = settings.value
        ) {
            val s = settings.value
            value = when (s.themeMode) {
                ThemeMode.DARK -> darkAppColors(
                    accent = s.accentColor,
                    gradientStart = s.gradientStart,
                    gradientEnd = s.gradientEnd,
                    cardOpacity = s.cardOpacity
                )
                ThemeMode.LIGHT -> lightAppColors(
                    accent = s.accentColor,
                    gradientStart = s.gradientStart,
                    gradientEnd = s.gradientEnd,
                    cardOpacity = s.cardOpacity
                )
                ThemeMode.AMOLED -> amoledAppColors(
                    accent = s.accentColor,
                    cardOpacity = s.cardOpacity
                )
            }
        }
    }
}
