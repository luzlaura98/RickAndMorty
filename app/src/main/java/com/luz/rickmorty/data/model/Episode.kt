package com.luz.rickmorty.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Luz on 8/8/2022.
 */
data class Episode(
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String?,
    @SerializedName("episode")
    private val seasonEpisode : String?
)
