package com.luz.rickmorty.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.luz.rickmorty.data.model.Character
import com.luz.rickmorty.data.network.RickAndMortyClient
import okio.IOException
import retrofit2.HttpException

/**
 * Created by Luz on 7/8/2022.
 *
 * <Key, Value> <Identiffier for load data, data type en sí>
 *     <ID,Character>
 */
class CharactersPagingSource(
    private val service: RickAndMortyClient
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        //El objeto LoadParams contiene información sobre la operación de carga
        //que se realizará. Esto incluye la clave y la cantidad de elementos que
        //se cargarán.

        //El objeto LoadResult contiene el resultado de la operación de carga.
        //LoadResult es una clase sellada que toma una de dos posibles formas
        //en virtud de si la llamada a load() se realizó correctamente o no
        //LoadResult.Page || LoadResult.Error

        return try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber: Int = params.key ?: 1
            val response = service.getCharacters(nextPageNumber)
            return LoadResult.Page(
                data = response.results,
                prevKey = response.info?.prevPage, //Only paging forward
                nextKey = response.info?.nextPage
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}