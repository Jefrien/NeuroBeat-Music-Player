package dev.jefrien.neurobeat.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.jefrien.neurobeat.data.local.datastore.SettingsDataStore
import dev.jefrien.neurobeat.data.remote.api.BaseUrlInterceptor
import dev.jefrien.neurobeat.data.remote.api.SubsonicApi
import dev.jefrien.neurobeat.data.remote.api.SubsonicAuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        settingsDataStore: SettingsDataStore
    ): SubsonicAuthInterceptor = SubsonicAuthInterceptor(settingsDataStore)

    @Provides
    @Singleton
    fun provideBaseUrlInterceptor(
        settingsDataStore: SettingsDataStore
    ): BaseUrlInterceptor = BaseUrlInterceptor(settingsDataStore)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: SubsonicAuthInterceptor,
        baseUrlInterceptor: BaseUrlInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(baseUrlInterceptor) // First: rewrite URL
            .addInterceptor(authInterceptor)    // Second: add auth params
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        // Dummy base URL - will be rewritten by BaseUrlInterceptor at runtime
        return Retrofit.Builder()
            .baseUrl("http://localhost/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSubsonicApi(retrofit: Retrofit): SubsonicApi {
        return retrofit.create(SubsonicApi::class.java)
    }
}
