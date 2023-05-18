package com.victorvgc.cstv.core.utils

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Locale

fun String.toDateString(context: Context, stringDateFormat: String = "yyyy-MM-ddTHH:mm:SSZ"): String {
    val format = SimpleDateFormat(stringDateFormat, Locale.getDefault())

    return "TBA"
}