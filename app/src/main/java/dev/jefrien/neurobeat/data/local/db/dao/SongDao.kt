package dev.jefrien.neurobeat.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.jefrien.neurobeat.data.local.db.entity.SongEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {

    @Query("SELECT * FROM songs ORDER BY title ASC")
    fun getAll(): Flow<List<SongEntity>>

    @Query("SELECT * FROM songs WHERE albumId = :albumId ORDER BY track ASC")
    fun getByAlbum(albumId: String): Flow<List<SongEntity>>

    @Query("SELECT * FROM songs WHERE artistId = :artistId ORDER BY title ASC")
    fun getByArtist(artistId: String): Flow<List<SongEntity>>

    @Query("SELECT * FROM songs WHERE genre = :genre ORDER BY title ASC")
    fun getByGenre(genre: String): Flow<List<SongEntity>>

    @Query("SELECT * FROM songs WHERE isAvailableOffline = 1 ORDER BY title ASC")
    fun getOfflineSongs(): Flow<List<SongEntity>>

    @Query("SELECT * FROM songs WHERE id = :id")
    suspend fun getById(id: String): SongEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(songs: List<SongEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(song: SongEntity)

    @Query("UPDATE songs SET isAvailableOffline = :isOffline, localPath = :localPath WHERE id = :id")
    suspend fun updateOfflineStatus(id: String, isOffline: Boolean, localPath: String?)

    @Query("DELETE FROM songs")
    suspend fun clearAll()
}
