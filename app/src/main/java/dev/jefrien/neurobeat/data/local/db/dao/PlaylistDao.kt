package dev.jefrien.neurobeat.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.jefrien.neurobeat.data.local.db.entity.PlaylistEntity
import dev.jefrien.neurobeat.data.local.db.entity.PlaylistSongEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {

    @Query("SELECT * FROM playlists ORDER BY name ASC")
    fun getAll(): Flow<List<PlaylistEntity>>

    @Query("SELECT * FROM playlists WHERE isAvailableOffline = 1 ORDER BY name ASC")
    fun getOfflinePlaylists(): Flow<List<PlaylistEntity>>

    @Query("SELECT * FROM playlists WHERE id = :id")
    suspend fun getById(id: String): PlaylistEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(playlists: List<PlaylistEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(playlist: PlaylistEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistSongs(playlistSongs: List<PlaylistSongEntity>)

    @Query("SELECT * FROM playlist_songs WHERE playlistId = :playlistId ORDER BY position ASC")
    suspend fun getPlaylistSongs(playlistId: String): List<PlaylistSongEntity>

    @Query("DELETE FROM playlists")
    suspend fun clearAll()

    @Query("DELETE FROM playlist_songs WHERE playlistId = :playlistId")
    suspend fun clearPlaylistSongs(playlistId: String)
}
