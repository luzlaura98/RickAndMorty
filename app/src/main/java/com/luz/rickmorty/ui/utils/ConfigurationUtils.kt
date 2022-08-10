package com.luz.rickmorty.ui.utils

import android.content.res.Configuration
import android.content.res.Resources

/**
 * Created by Luz on 9/8/2022.
 */

fun isLandscape(resources: Resources) : Boolean{
    val orientation = resources.configuration.orientation
    return orientation == Configuration.ORIENTATION_LANDSCAPE
}

fun isDarkTheme(resources: Resources): Boolean {
    return resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}