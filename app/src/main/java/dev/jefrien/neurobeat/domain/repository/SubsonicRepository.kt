package dev.jefrien.neurobeat.domain.repository

import dev.jefrien.neurobeat.domain.model.Album
import dev.jefrien.neurobeat.domain.model.Artist
import dev.jefrien.neurobeat.domain.model.Genre
import dev.jefrien.neurobeat.domain.model.Playlist
import dev.jefrien.neurobeat.domain.model.Song

interface SubsonicRepository {
    suspend fun ping(): Result<Boolean>
    suspend fun getArtists(): Result<List<Artist>>
    suspend fun getArtist(id: String): Result<Artist>
    suspend fun getAlbum(id: String): Result<Album>
    suspend fun getSong(id: String): Result<Song>
    suspend fun getGenres(): Result<List<Genre>>
    suspend fun getAlbums(type: String = "newest", size: Int = 50): Result<List<Album>>
    suspend fun getRandomSongs(size: Int = 50): Result<List<Song>>
    suspend fun getSongsByGenre(genre: String, count: Int = 50): Result<List<Song>>
    suspend fun search(query: String): Result<Triple<List<Artist>, List<Album>, List<Song>>>
    suspend fun getPlaylists(): Result<List<Playlist>>
    suspend fun getPlaylist(id: String): Result<Playlist>
    suspend fun getStarred(): Result<Triple<List<Artist>, List<Album>, List<Song>>>
}
