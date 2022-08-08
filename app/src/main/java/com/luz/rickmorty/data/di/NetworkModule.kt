package com.luz.rickmorty.data.di

import android.util.Log
import com.luz.rickmorty.BuildConfig
import com.luz.rickmorty.data.network.RickAndMortyClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Luz on 3/8/2022.
 */
@Module
@InstallIn(SingletonComponent::class) //application level
object NetworkModule {

    const val BASE_URL = "https://rickandmortyapi.com/api/"

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit{

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG){
            val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
                Log.i("ApiClient", message)
            }
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addInterceptor(httpLoggingInterceptor)
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient.build())
            .build()
    }

    @Singleton
    @Provides
    fun provideClient(retrofit: Retrofit) : RickAndMortyClient{
        return retrofit.create(RickAndMortyClient::class.java)
    }
}