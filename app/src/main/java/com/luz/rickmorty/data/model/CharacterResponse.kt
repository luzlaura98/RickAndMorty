package com.luz.rickmorty.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Luz on 3/8/2022.
 */
data class CharacterResponse(
    @SerializedName("error")
    val error : String?,
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String?
)
