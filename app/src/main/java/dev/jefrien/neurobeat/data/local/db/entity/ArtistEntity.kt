package dev.jefrien.neurobeat.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class ArtistEntity(
    @PrimaryKey val id: String,
    val name: String,
    val coverArtId: String? = null,
    val albumCount: Int = 0,
    val songCount: Int = 0,
    val isStarred: Boolean = false,
    val isAvailableOffline: Boolean = false,
    val syncedAt: Long = System.currentTimeMillis()
)
