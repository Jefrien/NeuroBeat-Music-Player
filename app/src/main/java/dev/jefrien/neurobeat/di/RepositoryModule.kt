package dev.jefrien.neurobeat.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.jefrien.neurobeat.data.repository.SubsonicRepositoryImpl
import dev.jefrien.neurobeat.domain.repository.SubsonicRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSubsonicRepository(
        impl: SubsonicRepositoryImpl
    ): SubsonicRepository
}
