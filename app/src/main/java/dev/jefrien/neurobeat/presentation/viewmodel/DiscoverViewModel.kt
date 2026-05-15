package dev.jefrien.neurobeat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jefrien.neurobeat.domain.model.Genre
import dev.jefrien.neurobeat.domain.model.Song
import dev.jefrien.neurobeat.domain.repository.SubsonicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val repository: SubsonicRepository
) : ViewModel() {

    sealed class DiscoverState {
        data object Loading : DiscoverState()
        data class Error(val message: String) : DiscoverState()
        data class Success(
            val randomSongs: List<Song>,
            val genres: List<Genre>,
            val albums: List<dev.jefrien.neurobeat.domain.model.Album>
        ) : DiscoverState()
    }

    private val _state = MutableStateFlow<DiscoverState>(DiscoverState.Loading)
    val state: StateFlow<DiscoverState> = _state

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = DiscoverState.Loading

            val songsResult = repository.getRandomSongs(20)
            val genresResult = repository.getGenres()
            val albumsResult = repository.getAlbums("newest", 20)

            val songs = songsResult.getOrNull() ?: emptyList()
            val genres = genresResult.getOrNull() ?: emptyList()
            val albums = albumsResult.getOrNull() ?: emptyList()

            if (songsResult.isFailure && genresResult.isFailure) {
                _state.value = DiscoverState.Error(
                    songsResult.exceptionOrNull()?.message ?: "Failed to load data"
                )
            } else {
                _state.value = DiscoverState.Success(songs, genres, albums)
            }
        }
    }
}
