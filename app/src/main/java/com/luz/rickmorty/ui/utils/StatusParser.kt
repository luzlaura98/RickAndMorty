package com.luz.rickmorty.ui.utils

/**
 * Created by Luz on 8/8/2022.
 */
class StatusConverter {
    companion object{
        fun from(value : String?) : StatusView.Status{
            value?:return StatusView.Status.Unknown
            return when{
                "dead" == value.lowercase() -> StatusView.Status.Dead
                "alive" == value.lowercase() -> StatusView.Status.Alive
                else -> StatusView.Status.Unknown
            }
        }
    }
}