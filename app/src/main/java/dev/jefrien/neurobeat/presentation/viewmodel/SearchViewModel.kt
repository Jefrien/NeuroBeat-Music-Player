package dev.jefrien.neurobeat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jefrien.neurobeat.domain.model.Album
import dev.jefrien.neurobeat.domain.model.Artist
import dev.jefrien.neurobeat.domain.model.Song
import dev.jefrien.neurobeat.domain.repository.SubsonicRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SubsonicRepository
) : ViewModel() {

    data class SearchState(
        val query: String = "",
        val artists: List<Artist> = emptyList(),
        val albums: List<Album> = emptyList(),
        val songs: List<Song> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
        val hasSearched: Boolean = false
    )

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state

    private val _queryFlow = MutableStateFlow("")

    init {
        _queryFlow
            .debounce(300)
            .onEach { query ->
                if (query.isNotBlank()) {
                    performSearch(query)
                } else {
                    _state.value = _state.value.copy(
                        artists = emptyList(),
                        albums = emptyList(),
                        songs = emptyList(),
                        hasSearched = false,
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onQueryChange(query: String) {
        _state.value = _state.value.copy(query = query)
        _queryFlow.value = query
    }

    private suspend fun performSearch(query: String) {
        _state.value = _state.value.copy(isLoading = true, error = null, hasSearched = true)

        val result = repository.search(query)
        result.fold(
            onSuccess = { (artists, albums, songs) ->
                _state.value = _state.value.copy(
                    artists = artists,
                    albums = albums,
                    songs = songs,
                    isLoading = false,
                    error = null
                )
            },
            onFailure = { error ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = error.message ?: "Search failed"
                )
            }
        )
    }
}
