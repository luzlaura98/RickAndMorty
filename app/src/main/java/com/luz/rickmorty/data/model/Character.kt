package com.luz.rickmorty.data.model

/**
 * Created by Luz on 3/8/2022.
 */
data class Character(
    val id: Int,
    val name: String?,
    val image: String?
) {
    constructor(c: CharacterResponse) : this(c.id, c.name, c.image)
}
