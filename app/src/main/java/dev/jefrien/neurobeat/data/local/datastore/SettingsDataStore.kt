package dev.jefrien.neurobeat.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.jefrien.neurobeat.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "neurobeat_settings")

@Singleton
class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val dataStore = context.dataStore

    companion object Keys {
        val SERVER_URL = stringPreferencesKey("server_url")
        val USERNAME = stringPreferencesKey("username")
        val TOKEN = stringPreferencesKey("token")
        val SALT = stringPreferencesKey("salt")
        val LOGGED_IN = booleanPreferencesKey("logged_in")

        val THEME_MODE = stringPreferencesKey("theme_mode")
        val ACCENT_COLOR = stringPreferencesKey("accent_color")
        val GRADIENT_START = stringPreferencesKey("gradient_start")
        val GRADIENT_END = stringPreferencesKey("gradient_end")
        val CARD_OPACITY = floatPreferencesKey("card_opacity")

        val STREAM_QUALITY = stringPreferencesKey("stream_quality")
        val DOWNLOAD_QUALITY = stringPreferencesKey("download_quality")
        val CACHE_SIZE_MB = intPreferencesKey("cache_size_mb")
        val OFFLINE_LIMIT_MB = intPreferencesKey("offline_limit_mb")
    }

    // Server credentials
    val serverUrl: Flow<String> = dataStore.data.map { it[SERVER_URL] ?: "" }
    val username: Flow<String> = dataStore.data.map { it[USERNAME] ?: "" }
    val token: Flow<String> = dataStore.data.map { it[TOKEN] ?: "" }
    val salt: Flow<String> = dataStore.data.map { it[SALT] ?: "" }
    val isLoggedIn: Flow<Boolean> = dataStore.data.map { it[LOGGED_IN] ?: false }

    suspend fun saveCredentials(serverUrl: String, username: String, token: String, salt: String) {
        dataStore.edit { prefs ->
            prefs[SERVER_URL] = serverUrl
            prefs[USERNAME] = username
            prefs[TOKEN] = token
            prefs[SALT] = salt
            prefs[LOGGED_IN] = true
        }
    }

    suspend fun clearCredentials() {
        dataStore.edit { prefs ->
            prefs.remove(SERVER_URL)
            prefs.remove(USERNAME)
            prefs.remove(TOKEN)
            prefs.remove(SALT)
            prefs[LOGGED_IN] = false
        }
    }

    // Theme settings
    val themeMode: Flow<ThemeMode> = dataStore.data.map {
        ThemeMode.valueOf(it[THEME_MODE] ?: ThemeMode.DARK.name)
    }
    val accentColor: Flow<String> = dataStore.data.map { it[ACCENT_COLOR] ?: "FFE040FB" }
    val gradientStart: Flow<String> = dataStore.data.map { it[GRADIENT_START] ?: "FF0A0A1A" }
    val gradientEnd: Flow<String> = dataStore.data.map { it[GRADIENT_END] ?: "FF1A0A2E" }
    val cardOpacity: Flow<Float> = dataStore.data.map { it[CARD_OPACITY] ?: 0.12f }

    suspend fun saveThemeMode(mode: ThemeMode) {
        dataStore.edit { it[THEME_MODE] = mode.name }
    }

    suspend fun saveAccentColor(color: String) {
        dataStore.edit { it[ACCENT_COLOR] = color }
    }

    suspend fun saveGradient(start: String, end: String) {
        dataStore.edit {
            it[GRADIENT_START] = start
            it[GRADIENT_END] = end
        }
    }

    suspend fun saveCardOpacity(opacity: Float) {
        dataStore.edit { it[CARD_OPACITY] = opacity }
    }
}
