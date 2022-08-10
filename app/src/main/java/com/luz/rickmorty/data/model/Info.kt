package com.luz.rickmorty.data.model

/**
 * Created by Luz on 7/8/2022.
 */
data class Info(
    val pages: Int,
    private val next: String?,
    private val prev: String?
) {
    var nextPage: Int? = null
        private set
        get() {
            if (field != null) return field
            val p = next?.replace(("[^\\d-]+").toRegex(), "")?.toIntOrNull()
            field = if (p == null || p <= 0) null else p
            return field
        }

    var prevPage: Int? = null
        private set
        get() {
            if (field != null) return field
            val p = prev?.replace(("[^\\d-]+").toRegex(), "")?.toIntOrNull()
            field = if (p == null || p <= 0) null else p
            return field
        }
}
