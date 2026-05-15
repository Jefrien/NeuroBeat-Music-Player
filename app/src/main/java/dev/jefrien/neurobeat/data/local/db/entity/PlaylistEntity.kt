package dev.jefrien.neurobeat.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey val id: String,
    val name: String,
    val songCount: Int = 0,
    val duration: Int = 0,
    val created: Long? = null,
    val changed: Long? = null,
    val isPublic: Boolean = false,
    val owner: String? = null,
    val isAvailableOffline: Boolean = false,
    val syncedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "playlist_songs", primaryKeys = ["playlistId", "songId"])
data class PlaylistSongEntity(
    val playlistId: String,
    val songId: String,
    val position: Int
)
