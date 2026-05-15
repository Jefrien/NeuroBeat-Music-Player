package dev.jefrien.neurobeat.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SubsonicResponse(
    @SerializedName("subsonic-response") val subsonicResponse: ResponseBody?
)

data class ResponseBody(
    val status: String,
    val version: String,
    val error: SubsonicError? = null,
    val artists: ArtistsWrapper? = null,
    val artist: ArtistDto? = null,
    val album: AlbumDto? = null,
    val song: SongDto? = null,
    val genres: GenresWrapper? = null,
    val albumList2: AlbumListWrapper? = null,
    val randomSongs: SongsWrapper? = null,
    val songsByGenre: SongsWrapper? = null,
    val searchResult3: SearchResult3? = null,
    val playlists: PlaylistsWrapper? = null,
    val playlist: PlaylistDto? = null,
    val starred2: Starred2? = null,
    val lyrics: LyricsDto? = null
)

data class SubsonicError(
    val code: Int,
    val message: String
)

data class ArtistsWrapper(
    val index: List<IndexDto>? = null
)

data class IndexDto(
    val name: String,
    val artist: List<ArtistDto>? = null
)

data class ArtistDto(
    val id: String,
    val name: String,
    val coverArt: String? = null,
    val albumCount: Int? = null,
    val album: List<AlbumDto>? = null
)

data class AlbumDto(
    val id: String,
    val name: String,
    val artist: String? = null,
    val artistId: String? = null,
    val coverArt: String? = null,
    val songCount: Int? = null,
    val duration: Int? = null,
    val year: Int? = null,
    val genre: String? = null,
    val song: List<SongDto>? = null,
    val starred: String? = null,
    val rating: Int? = null,
    val created: String? = null
)

data class SongDto(
    val id: String,
    val title: String,
    val artist: String? = null,
    val artistId: String? = null,
    val album: String? = null,
    val albumId: String? = null,
    val duration: Int? = null,
    val track: Int? = null,
    val year: Int? = null,
    val genre: String? = null,
    val coverArt: String? = null,
    val contentType: String? = null,
    val bitRate: Int? = null,
    val path: String? = null,
    val starred: String? = null,
    val rating: Int? = null,
    val playCount: Int? = null
)

data class GenresWrapper(
    val genre: List<GenreDto>? = null
)

data class GenreDto(
    val value: String,
    val songCount: Int? = null,
    val albumCount: Int? = null
)

data class AlbumListWrapper(
    val album: List<AlbumDto>? = null
)

data class SongsWrapper(
    val song: List<SongDto>? = null
)

data class SearchResult3(
    val artist: List<ArtistDto>? = null,
    val album: List<AlbumDto>? = null,
    val song: List<SongDto>? = null
)

data class PlaylistsWrapper(
    val playlist: List<PlaylistDto>? = null
)

data class PlaylistDto(
    val id: String,
    val name: String,
    val songCount: Int? = null,
    val duration: Int? = null,
    val created: String? = null,
    val changed: String? = null,
    val public: Boolean? = null,
    val owner: String? = null,
    val entry: List<SongDto>? = null
)

data class Starred2(
    val artist: List<ArtistDto>? = null,
    val album: List<AlbumDto>? = null,
    val song: List<SongDto>? = null
)

data class LyricsDto(
    val artist: String? = null,
    val title: String? = null,
    val value: String? = null
)
