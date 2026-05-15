package dev.jefrien.neurobeat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jefrien.neurobeat.app.theme.ThemeManager
import dev.jefrien.neurobeat.data.local.datastore.SettingsDataStore
import dev.jefrien.neurobeat.domain.model.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore,
    private val themeManager: ThemeManager
) : ViewModel() {

    private val _themeMode = MutableStateFlow(ThemeMode.DARK)
    val themeMode: StateFlow<ThemeMode> = _themeMode

    private val _accentColor = MutableStateFlow(androidx.compose.ui.graphics.Color(0xFFE040FB))
    val accentColor: StateFlow<androidx.compose.ui.graphics.Color> = _accentColor

    private val _cardOpacity = MutableStateFlow(0.12f)
    val cardOpacity: StateFlow<Float> = _cardOpacity

    init {
        viewModelScope.launch {
            settingsDataStore.themeMode.collect { _themeMode.value = it }
        }
        viewModelScope.launch {
            settingsDataStore.cardOpacity.collect { _cardOpacity.value = it }
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            settingsDataStore.saveThemeMode(mode)
            themeManager.updateThemeMode(mode)
        }
    }

    fun setCardOpacity(opacity: Float) {
        viewModelScope.launch {
            settingsDataStore.saveCardOpacity(opacity)
            themeManager.updateCardOpacity(opacity)
        }
    }

    fun logout() {
        viewModelScope.launch {
            settingsDataStore.clearCredentials()
        }
    }
}
