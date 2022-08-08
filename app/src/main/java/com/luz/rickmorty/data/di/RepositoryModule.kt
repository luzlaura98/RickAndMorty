package com.luz.rickmorty.data.di

import android.app.Application
import com.luz.rickmorty.data.network.RickAndMortyClient
import com.luz.rickmorty.data.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Luz on 4/8/2022.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCharacterRepository(
        application: Application,
        appService : RickAndMortyClient
    ) = CharacterRepository(application, appService)
}