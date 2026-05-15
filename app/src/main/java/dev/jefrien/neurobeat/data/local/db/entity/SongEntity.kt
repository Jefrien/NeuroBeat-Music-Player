package dev.jefrien.neurobeat.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class SongEntity(
    @PrimaryKey val id: String,
    val title: String,
    val artistId: String,
    val artistName: String,
    val albumId: String,
    val albumName: String,
    val duration: Int = 0,
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
    val isAvailableOffline: Boolean = false,
    val downloadStatus: String = "NONE",
    val syncedAt: Long = System.currentTimeMillis()
)
