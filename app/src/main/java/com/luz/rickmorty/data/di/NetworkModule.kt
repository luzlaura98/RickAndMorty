package com.luz.rickmorty.data.di

import com.luz.rickmorty.data.network.RickAndMortyClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Luz on 3/8/2022.
 */
@Module
@InstallIn(SingletonComponent::class) //application level
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideClient(retrofit: Retrofit) : RickAndMortyClient{
        return retrofit.create(RickAndMortyClient::class.java)
    }
}