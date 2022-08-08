package com.luz.rickmorty.data.network

import com.luz.rickmorty.data.model.BaseListResponse
import com.luz.rickmorty.data.model.Character
import com.luz.rickmorty.data.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Luz on 3/8/2022.
 */
interface RickAndMortyClient {

    @GET("character/")
    suspend fun getCharacters(@Query("page") page : Int) : BaseListResponse<Character>

    @GET("character/{characterId}")
    suspend fun getCharacter(@Path("characterId") characterId : Int) : CharacterResponse

}