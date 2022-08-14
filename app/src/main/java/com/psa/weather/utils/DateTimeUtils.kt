package com.psa.weather.utils

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    private val LOCALE = Locale.ENGLISH
    private const val PATTERN = "E hh:mm a"
    private val dateFormat = SimpleDateFormat(PATTERN, LOCALE)

    fun getStrDate(date: Date?, pattern: String?) = try {
        dateFormat.applyPattern(pattern)
        dateFormat.format(date)
    } catch (e: Exception) {
        null
    }

    fun getDate(time: Long) = getStrDate(
        Date(time * 1000), PATTERN
    )?.replaceFirstChar { it.uppercase() }

}