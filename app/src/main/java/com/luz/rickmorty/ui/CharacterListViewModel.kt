package com.luz.rickmorty.ui

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.luz.rickmorty.data.model.Character
import com.luz.rickmorty.data.repository.CharacterRepository
import com.luz.rickmorty.data.repository.CharactersPagingSource
import com.luz.rickmorty.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Luz on 3/8/2022.
 */
@HiltViewModel
class CharacterListViewModel @Inject constructor(
    application: Application,
    private val repository: CharacterRepository
) : BaseViewModel(application) {

    var lastCharacterSelected : Character? = null

    val myFlow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
    PagingConfig(pageSize = CharacterRepository.ITEMS_PER_PAGE)
    ){
        CharactersPagingSource(repository.appService)
    }.flow
        .cachedIn(viewModelScope)
}