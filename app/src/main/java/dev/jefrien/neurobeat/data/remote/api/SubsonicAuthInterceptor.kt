package dev.jefrien.neurobeat.data.remote.api

import dev.jefrien.neurobeat.data.local.datastore.SettingsDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.math.BigInteger
import java.security.MessageDigest
import java.security.SecureRandom
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubsonicAuthInterceptor @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.url.newBuilder()

        val username = runBlocking { settingsDataStore.username.first() }
        val token = runBlocking { settingsDataStore.token.first() }
        val salt = runBlocking { settingsDataStore.salt.first() }

        if (username.isNotEmpty() && token.isNotEmpty() && salt.isNotEmpty()) {
            builder.addQueryParameter("u", username)
            builder.addQueryParameter("t", token)
            builder.addQueryParameter("s", salt)
        }

        builder.addQueryParameter("v", "1.16.1")
        builder.addQueryParameter("c", "Neurobeat")
        builder.addQueryParameter("f", "json")

        val request = original.newBuilder()
            .url(builder.build())
            .build()

        return chain.proceed(request)
    }

    companion object {
        fun generateSalt(): String {
            val random = SecureRandom()
            val bytes = ByteArray(8)
            random.nextBytes(bytes)
            return BigInteger(1, bytes).toString(16).padStart(16, '0')
        }

        fun generateToken(password: String, salt: String): String {
            val md5 = MessageDigest.getInstance("MD5")
            return BigInteger(1, md5.digest((password + salt).toByteArray()))
                .toString(16).padStart(32, '0')
        }
    }
}
