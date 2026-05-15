package dev.jefrien.neurobeat.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey val id: String,
    val name: String,
    val artistId: String,
    val artistName: String,
    val coverArtId: String? = null,
    val songCount: Int = 0,
    val duration: Int = 0,
    val year: Int? = null,
    val genre: String? = null,
    val isStarred: Boolean = false,
    val rating: Int? = null,
    val created: Long? = null,
    val isAvailableOffline: Boolean = false,
    val syncedAt: Long = System.currentTimeMillis()
)
