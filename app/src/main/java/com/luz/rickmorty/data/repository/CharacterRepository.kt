package com.luz.rickmorty.data.repository

import android.app.Application
import com.luz.rickmorty.data.model.Character
import com.luz.rickmorty.data.network.Result
import com.luz.rickmorty.data.network.RickAndMortyClient
import com.luz.rickmorty.data.network.parseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Luz on 4/8/2022.
 */
class CharacterRepository @Inject constructor(
    private val application: Application,
    val appService : RickAndMortyClient
){
    companion object{
        const val FIRST_PAGE = 1
        const val ITEMS_PER_PAGE = 20
    }
    suspend fun getCharacters(page : Int = FIRST_PAGE) : Flow<Result<List<Character>>> {
        return flow {
            try {
                val response = appService.getCharacters(page)
                emit(Result.Success(response.results))
            }catch (e : Exception){
                e.printStackTrace()
                emit(Result.Error<List<Character>>(e.parseError(application)))
            }
        }
    }
}