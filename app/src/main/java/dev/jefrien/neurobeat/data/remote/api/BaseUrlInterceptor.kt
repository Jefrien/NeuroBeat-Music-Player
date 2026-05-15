package dev.jefrien.neurobeat.data.remote.api

import dev.jefrien.neurobeat.data.local.datastore.SettingsDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseUrlInterceptor @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url

        val serverUrl = runBlocking { settingsDataStore.serverUrl.first() }
        if (serverUrl.isBlank()) {
            return chain.proceed(original)
        }

        val baseUrl = if (serverUrl.endsWith("/")) serverUrl else "$serverUrl/"
        val baseHttpUrl = baseUrl.toHttpUrlOrNull() ?: return chain.proceed(original)

        // Build new URL combining base URL with endpoint path
        val basePath = baseHttpUrl.encodedPath.trimEnd('/')
        val endpointPath = originalUrl.encodedPath.trimStart('/')
        val newPath = if (basePath.isEmpty()) "/$endpointPath" else "$basePath/$endpointPath"

        val newUrl = originalUrl.newBuilder()
            .scheme(baseHttpUrl.scheme)
            .host(baseHttpUrl.host)
            .port(baseHttpUrl.port)
            .encodedPath(newPath)
            .build()

        val request = original.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(request)
    }
}
