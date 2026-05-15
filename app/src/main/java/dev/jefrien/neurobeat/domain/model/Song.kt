package dev.jefrien.neurobeat.domain.model

data class Song(
    val id: String,
    val title: String,
    val artistId: String,
    val artistName: String,
    val albumId: String,
    val albumName: String,
    val duration: Int,
    val track: Int? = null,
    val year: Int? = null,
    val genre: String? = null,
    val coverArtId: String? = null,
    val contentType: String? = null,
    val bitRate: Int? = null,
    val path: String? = null,
    val localPath: String? = null,
    val isStarred: Boolean = false,
    val rating: Int? = null,
    val lastPlayed: Long? = null,
    val playCount: Int = 0,
    val isAvailableOffline: Boolean = false
)
