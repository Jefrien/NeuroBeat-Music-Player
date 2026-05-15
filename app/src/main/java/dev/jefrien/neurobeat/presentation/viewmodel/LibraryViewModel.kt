package dev.jefrien.neurobeat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jefrien.neurobeat.domain.model.Album
import dev.jefrien.neurobeat.domain.model.Artist
import dev.jefrien.neurobeat.domain.model.Playlist
import dev.jefrien.neurobeat.domain.model.Song
import dev.jefrien.neurobeat.domain.repository.SubsonicRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val repository: SubsonicRepository
) : ViewModel() {

    data class LibraryState(
        val artists: List<Artist> = emptyList(),
        val albums: List<Album> = emptyList(),
        val songs: List<Song> = emptyList(),
        val playlists: List<Playlist> = emptyList(),
        val albumSongs: Map<String, List<Song>> = emptyMap(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _state = MutableStateFlow(LibraryState())
    val state: StateFlow<LibraryState> = _state

    fun loadAll() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            val artistsDeferred = async { repository.getArtists() }
            val albumsDeferred = async { repository.getAlbums("newest", 100) }
            val songsDeferred = async { repository.getRandomSongs(200) }
            val playlistsDeferred = async { repository.getPlaylists() }

            val artists = artistsDeferred.await().getOrNull() ?: emptyList()
            val albums = albumsDeferred.await().getOrNull() ?: emptyList()
            val songs = songsDeferred.await().getOrNull() ?: emptyList()
            val playlists = playlistsDeferred.await().getOrNull() ?: emptyList()

            // Load songs for each album
            val albumSongsMap = mutableMapOf<String, List<Song>>()
            albums.take(20).forEach { album ->
                val albumDetail = repository.getAlbum(album.id).getOrNull()
                if (albumDetail != null) {
                    // Album from API doesn't contain songs directly in our model,
                    // we'd need to fetch album details. For now, skip this optimization.
                }
            }

            _state.value = LibraryState(
                artists = artists,
                albums = albums,
                songs = songs.sortedBy { it.title },
                playlists = playlists,
                albumSongs = albumSongsMap,
                isLoading = false,
                error = null
            )
        }
    }
}
