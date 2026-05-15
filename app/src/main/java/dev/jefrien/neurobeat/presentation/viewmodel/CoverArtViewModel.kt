package dev.jefrien.neurobeat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jefrien.neurobeat.data.remote.api.CoverArtUrlBuilder
import javax.inject.Inject

@HiltViewModel
class CoverArtViewModel @Inject constructor(
    private val urlBuilder: CoverArtUrlBuilder
) : ViewModel() {

    fun buildCoverArtUrl(coverArtId: String?): String? {
        return urlBuilder.buildCoverArtUrl(coverArtId)
    }

    fun buildStreamUrl(songId: String): String? {
        return urlBuilder.buildStreamUrl(songId)
    }
}
