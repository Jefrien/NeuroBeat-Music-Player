package dev.jefrien.neurobeat.domain.model

data class Genre(
    val name: String,
    val songCount: Int = 0,
    val albumCount: Int = 0
)
