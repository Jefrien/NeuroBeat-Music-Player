package dev.jefrien.neurobeat.data.repository

import dev.jefrien.neurobeat.data.remote.api.SubsonicApi
import dev.jefrien.neurobeat.data.remote.dto.toDomain
import dev.jefrien.neurobeat.domain.model.Album
import dev.jefrien.neurobeat.domain.model.Artist
import dev.jefrien.neurobeat.domain.model.Genre
import dev.jefrien.neurobeat.domain.model.Playlist
import dev.jefrien.neurobeat.domain.model.Song
import dev.jefrien.neurobeat.domain.repository.SubsonicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubsonicRepositoryImpl @Inject constructor(
    private val api: SubsonicApi
) : SubsonicRepository {

    override suspend fun ping(): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val response = api.ping()
            val status = response.subsonicResponse?.status
            Result.success(status == "ok")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getArtists(): Result<List<Artist>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getArtists()
            val artists = response.subsonicResponse?.artists?.index
                ?.flatMap { it.artist ?: emptyList() }
                ?.map { it.toDomain() } ?: emptyList()
            Result.success(artists)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getArtist(id: String): Result<Artist> = withContext(Dispatchers.IO) {
        try {
            val response = api.getArtist(id)
            val artist = response.subsonicResponse?.artist?.toDomain()
                ?: return@withContext Result.failure(Exception("Artist not found"))
            Result.success(artist)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAlbum(id: String): Result<Album> = withContext(Dispatchers.IO) {
        try {
            val response = api.getAlbum(id)
            val album = response.subsonicResponse?.album?.toDomain()
                ?: return@withContext Result.failure(Exception("Album not found"))
            Result.success(album)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSong(id: String): Result<Song> = withContext(Dispatchers.IO) {
        try {
            val response = api.getSong(id)
            val song = response.subsonicResponse?.song?.toDomain()
                ?: return@withContext Result.failure(Exception("Song not found"))
            Result.success(song)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getGenres(): Result<List<Genre>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getGenres()
            val genres = response.subsonicResponse?.genres?.genre
                ?.map { it.toDomain() } ?: emptyList()
            Result.success(genres)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAlbums(type: String, size: Int): Result<List<Album>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getAlbumList(type, size)
            val albums = response.subsonicResponse?.albumList2?.album
                ?.map { it.toDomain() } ?: emptyList()
            Result.success(albums)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRandomSongs(size: Int): Result<List<Song>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getRandomSongs(size)
            val songs = response.subsonicResponse?.randomSongs?.song
                ?.map { it.toDomain() } ?: emptyList()
            Result.success(songs)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSongsByGenre(genre: String, count: Int): Result<List<Song>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getSongsByGenre(genre, count)
            val songs = response.subsonicResponse?.songsByGenre?.song
                ?.map { it.toDomain() } ?: emptyList()
            Result.success(songs)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun search(query: String): Result<Triple<List<Artist>, List<Album>, List<Song>>> = withContext(Dispatchers.IO) {
        try {
            val response = api.search3(query)
            val result = response.subsonicResponse?.searchResult3
            val artists = result?.artist?.map { it.toDomain() } ?: emptyList()
            val albums = result?.album?.map { it.toDomain() } ?: emptyList()
            val songs = result?.song?.map { it.toDomain() } ?: emptyList()
            Result.success(Triple(artists, albums, songs))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPlaylists(): Result<List<Playlist>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getPlaylists()
            val playlists = response.subsonicResponse?.playlists?.playlist
                ?.map { it.toDomain() } ?: emptyList()
            Result.success(playlists)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPlaylist(id: String): Result<Playlist> = withContext(Dispatchers.IO) {
        try {
            val response = api.getPlaylist(id)
            val playlist = response.subsonicResponse?.playlist?.toDomain()
                ?: return@withContext Result.failure(Exception("Playlist not found"))
            Result.success(playlist)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getStarred(): Result<Triple<List<Artist>, List<Album>, List<Song>>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getStarred()
            val starred = response.subsonicResponse?.starred2
            val artists = starred?.artist?.map { it.toDomain() } ?: emptyList()
            val albums = starred?.album?.map { it.toDomain() } ?: emptyList()
            val songs = starred?.song?.map { it.toDomain() } ?: emptyList()
            Result.success(Triple(artists, albums, songs))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
