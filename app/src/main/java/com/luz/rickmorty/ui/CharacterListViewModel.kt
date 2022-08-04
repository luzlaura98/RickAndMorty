package com.luz.rickmorty.ui

import android.app.Application
import com.luz.rickmorty.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Luz on 3/8/2022.
 */
@HiltViewModel
class CharacterListViewModel  @Inject constructor(
    application: Application,
    //private val repository: CharacterRepository,
) : BaseViewModel(application)  {

}