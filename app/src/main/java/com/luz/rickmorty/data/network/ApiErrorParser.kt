package com.luz.rickmorty.data.network

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.luz.rickmorty.R
import okio.IOException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by Luz on 4/8/2022.
 */
fun Throwable.parseError(context: Context): String {
    return context.getString(
        when (this) {
            is HttpException -> if (code() in 500..599) R.string.server_error else R.string.default_message_error
            is SocketTimeoutException -> R.string.time_out_error
            is ConnectException -> R.string.port_connection_error
            is UnknownHostException -> R.string.internet_error
            is JsonParseException -> R.string.default_message_error
            is IOException -> R.string.default_message_error
            else -> R.string.default_message_error
        }
    )
}