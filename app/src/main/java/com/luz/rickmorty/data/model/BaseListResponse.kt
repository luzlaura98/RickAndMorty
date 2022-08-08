package com.luz.rickmorty.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Luz on 3/8/2022.
 */
data class BaseListResponse<T>(

    @SerializedName("info")
    val info : Info?,

    @SerializedName("results")
    val results : List<T>
)