package com.luz.rickmorty.data.model

/**
 * Created by Luz on 7/8/2022.
 */
data class Info(
    val count: Int = 0,
    val pages: Int,
    private val next: String?,
    private val prev: String?
) {
    val nextPage: Int?
        get() {
            return next?.replace(("[^\\d]+").toRegex(), "")?.toIntOrNull()
        }

    val prevPage: Int?
        get() {
            return prev?.replace(("[^\\d]+").toRegex(), "")?.toIntOrNull()
        }
}
