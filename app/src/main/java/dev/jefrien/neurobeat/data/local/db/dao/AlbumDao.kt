package dev.jefrien.neurobeat.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.jefrien.neurobeat.data.local.db.entity.AlbumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Query("SELECT * FROM albums ORDER BY name ASC")
    fun getAll(): Flow<List<AlbumEntity>>

    @Query("SELECT * FROM albums WHERE artistId = :artistId ORDER BY year DESC")
    fun getByArtist(artistId: String): Flow<List<AlbumEntity>>

    @Query("SELECT * FROM albums WHERE isAvailableOffline = 1 ORDER BY name ASC")
    fun getOfflineAlbums(): Flow<List<AlbumEntity>>

    @Query("SELECT * FROM albums WHERE id = :id")
    suspend fun getById(id: String): AlbumEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<AlbumEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(album: AlbumEntity)

    @Query("DELETE FROM albums")
    suspend fun clearAll()
}
