package dev.jefrien.neurobeat.domain.model

data class Playlist(
    val id: String,
    val name: String,
    val songCount: Int = 0,
    val duration: Int = 0,
    val created: Long? = null,
    val changed: Long? = null,
    val isPublic: Boolean = false,
    val owner: String? = null,
    val isAvailableOffline: Boolean = false,
    val songs: List<Song> = emptyList()
)
