package com.psa.weather.utils

import android.content.Context

fun Context.getDrawableIdByName(name: String) = resources.getIdentifier(
    "ic_$name",
    "drawable",
    packageName
)