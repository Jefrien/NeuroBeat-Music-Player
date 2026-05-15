package dev.jefrien.neurobeat.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.jefrien.neurobeat.data.local.db.MusicDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MusicDatabase {
        return Room.databaseBuilder(
            context,
            MusicDatabase::class.java,
            "neurobeat.db"
        ).build()
    }

    @Provides
    fun provideSongDao(database: MusicDatabase) = database.songDao()

    @Provides
    fun provideAlbumDao(database: MusicDatabase) = database.albumDao()

    @Provides
    fun provideArtistDao(database: MusicDatabase) = database.artistDao()

    @Provides
    fun providePlaylistDao(database: MusicDatabase) = database.playlistDao()
}
