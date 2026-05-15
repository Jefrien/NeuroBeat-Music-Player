package dev.jefrien.neurobeat.data.remote.api

import dev.jefrien.neurobeat.data.remote.dto.SubsonicResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SubsonicApi {

    @GET("rest/ping")
    suspend fun ping(): SubsonicResponse

    @GET("rest/getArtists")
    suspend fun getArtists(
        @Query("musicFolderId") musicFolderId: String? = null
    ): SubsonicResponse

    @GET("rest/getArtist")
    suspend fun getArtist(
        @Query("id") id: String
    ): SubsonicResponse

    @GET("rest/getAlbum")
    suspend fun getAlbum(
        @Query("id") id: String
    ): SubsonicResponse

    @GET("rest/getSong")
    suspend fun getSong(
        @Query("id") id: String
    ): SubsonicResponse

    @GET("rest/getGenres")
    suspend fun getGenres(): SubsonicResponse

    @GET("rest/getAlbumList2")
    suspend fun getAlbumList(
        @Query("type") type: String = "newest",
        @Query("size") size: Int = 50,
        @Query("offset") offset: Int = 0
    ): SubsonicResponse

    @GET("rest/getRandomSongs")
    suspend fun getRandomSongs(
        @Query("size") size: Int = 50
    ): SubsonicResponse

    @GET("rest/getSongsByGenre")
    suspend fun getSongsByGenre(
        @Query("genre") genre: String,
        @Query("count") count: Int = 50
    ): SubsonicResponse

    @GET("rest/search3")
    suspend fun search3(
        @Query("query") query: String,
        @Query("artistCount") artistCount: Int = 20,
        @Query("albumCount") albumCount: Int = 20,
        @Query("songCount") songCount: Int = 20
    ): SubsonicResponse

    @GET("rest/getPlaylists")
    suspend fun getPlaylists(): SubsonicResponse

    @GET("rest/getPlaylist")
    suspend fun getPlaylist(
        @Query("id") id: String
    ): SubsonicResponse

    @GET("rest/getStarred2")
    suspend fun getStarred(): SubsonicResponse

    @GET("rest/getLyrics")
    suspend fun getLyrics(
        @Query("artist") artist: String,
        @Query("title") title: String
    ): SubsonicResponse

    @GET("rest/stream")
    suspend fun stream(
        @Query("id") id: String,
        @Query("maxBitRate") maxBitRate: Int? = null
    ): retrofit2.Response<okhttp3.ResponseBody>

    @GET("rest/download")
    suspend fun download(
        @Query("id") id: String
    ): retrofit2.Response<okhttp3.ResponseBody>

    @GET("rest/getCoverArt")
    suspend fun getCoverArt(
        @Query("id") id: String,
        @Query("size") size: Int? = null
    ): retrofit2.Response<okhttp3.ResponseBody>

    @GET("rest/scrobble")
    suspend fun scrobble(
        @Query("id") id: String,
        @Query("time") time: Long? = null,
        @Query("submission") submission: Boolean = true
    ): SubsonicResponse
}
