package dev.jefrien.neurobeat.domain.model

data class PlayQueue(
    val songs: List<Song> = emptyList(),
    val currentIndex: Int = 0,
    val positionMs: Long = 0,
    val isShuffled: Boolean = false,
    val repeatMode: RepeatMode = RepeatMode.OFF
)

enum class RepeatMode {
    OFF, ALL, ONE
}
