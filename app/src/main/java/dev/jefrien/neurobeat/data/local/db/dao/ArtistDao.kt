package dev.jefrien.neurobeat.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.jefrien.neurobeat.data.local.db.entity.ArtistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {

    @Query("SELECT * FROM artists ORDER BY name ASC")
    fun getAll(): Flow<List<ArtistEntity>>

    @Query("SELECT * FROM artists WHERE isAvailableOffline = 1 ORDER BY name ASC")
    fun getOfflineArtists(): Flow<List<ArtistEntity>>

    @Query("SELECT * FROM artists WHERE id = :id")
    suspend fun getById(id: String): ArtistEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(artists: List<ArtistEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(artist: ArtistEntity)

    @Query("DELETE FROM artists")
    suspend fun clearAll()
}
