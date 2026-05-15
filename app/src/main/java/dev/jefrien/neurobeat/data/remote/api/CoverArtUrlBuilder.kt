package dev.jefrien.neurobeat.data.remote.api

import dev.jefrien.neurobeat.data.local.datastore.SettingsDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoverArtUrlBuilder @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) {

    fun buildCoverArtUrl(coverArtId: String?, size: Int? = 300): String? {
        if (coverArtId == null) return null

        val serverUrl = runBlocking { settingsDataStore.serverUrl.first() }
        val username = runBlocking { settingsDataStore.username.first() }
        val token = runBlocking { settingsDataStore.token.first() }
        val salt = runBlocking { settingsDataStore.salt.first() }

        if (serverUrl.isBlank() || username.isBlank() || token.isBlank() || salt.isBlank()) {
            return null
        }

        val baseUrl = serverUrl.trimEnd('/')
        val url = StringBuilder("$baseUrl/rest/getCoverArt.view?id=$coverArtId")
        url.append("&u=$username")
        url.append("&t=$token")
        url.append("&s=$salt")
        url.append("&v=1.16.1")
        url.append("&c=Neurobeat")
        url.append("&f=json")
        if (size != null) {
            url.append("&size=$size")
        }
        return url.toString()
    }

    fun buildStreamUrl(songId: String, maxBitRate: Int? = null): String? {
        val serverUrl = runBlocking { settingsDataStore.serverUrl.first() }
        val username = runBlocking { settingsDataStore.username.first() }
        val token = runBlocking { settingsDataStore.token.first() }
        val salt = runBlocking { settingsDataStore.salt.first() }

        if (serverUrl.isBlank() || username.isBlank() || token.isBlank() || salt.isBlank()) {
            return null
        }

        val baseUrl = serverUrl.trimEnd('/')
        val url = StringBuilder("$baseUrl/rest/stream.view?id=$songId")
        url.append("&u=$username")
        url.append("&t=$token")
        url.append("&s=$salt")
        url.append("&v=1.16.1")
        url.append("&c=Neurobeat")
        url.append("&f=json")
        if (maxBitRate != null) {
            url.append("&maxBitRate=$maxBitRate")
        }
        return url.toString()
    }
}
