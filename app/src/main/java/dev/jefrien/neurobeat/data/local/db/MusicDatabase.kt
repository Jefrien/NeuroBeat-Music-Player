package dev.jefrien.neurobeat.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.jefrien.neurobeat.data.local.db.dao.AlbumDao
import dev.jefrien.neurobeat.data.local.db.dao.ArtistDao
import dev.jefrien.neurobeat.data.local.db.dao.PlaylistDao
import dev.jefrien.neurobeat.data.local.db.dao.SongDao
import dev.jefrien.neurobeat.data.local.db.entity.AlbumEntity
import dev.jefrien.neurobeat.data.local.db.entity.ArtistEntity
import dev.jefrien.neurobeat.data.local.db.entity.PlaylistEntity
import dev.jefrien.neurobeat.data.local.db.entity.PlaylistSongEntity
import dev.jefrien.neurobeat.data.local.db.entity.SongEntity

@Database(
    entities = [
        SongEntity::class,
        AlbumEntity::class,
        ArtistEntity::class,
        PlaylistEntity::class,
        PlaylistSongEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun albumDao(): AlbumDao
    abstract fun artistDao(): ArtistDao
    abstract fun playlistDao(): PlaylistDao
}
