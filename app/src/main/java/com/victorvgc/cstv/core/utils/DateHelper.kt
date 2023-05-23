package com.victorvgc.cstv.core.utils

import android.content.Context
import com.victorvgc.cstv.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun String?.toDateString(
    context: Context,
    inputFormat: String = "yyyy-MM-dd'T'HH:mm:SS'Z'",
    todayFormat: String = ", HH:mm",
    tomorrowFormat: String = "EEE, HH:mm",
    otherDatesFormat: String = "DD.MM, HH:mm"
): String {
    val formatFrom = SimpleDateFormat(inputFormat, Locale.getDefault())
    val formatToday = SimpleDateFormat(todayFormat, Locale.getDefault())
    val formatTomorrow = SimpleDateFormat(tomorrowFormat, Locale.getDefault())
    val formatToAlt = SimpleDateFormat(otherDatesFormat, Locale.getDefault())

    if (this != null) {
        return try {
            val formattedDate = formatFrom.parse(this)

            return when (formattedDate.getDateType()) {
                DateType.TODAY ->
                    context.getString(R.string.today) + formatToday.format(formattedDate!!)

                DateType.TOMORROW -> {
                    formatTomorrow.format(formattedDate!!)
                }

                DateType.AFTER -> {
                    formatToAlt.format(formattedDate!!)
                }

                DateType.UNKNOWN -> context.getString(R.string.to_be_announced)
                DateType.NOW -> context.getString(R.string.now)
            }
        } catch (e: Exception) {
            "Err 2"
        }
    }

    return context.getString(R.string.to_be_announced)
}

fun Date?.getDateType(): DateType {
    if (this == null) {
        return DateType.UNKNOWN
    }

    val current = Calendar.getInstance()
    val calendar = Calendar.getInstance()

    current.time = this

    if (calendar[Calendar.YEAR] == current[Calendar.YEAR]) {
        if (calendar[Calendar.MONTH] == current[Calendar.MONTH]) {
            if (calendar[Calendar.DAY_OF_MONTH] == current[Calendar.DAY_OF_MONTH]) {
                return if (calendar[Calendar.HOUR_OF_DAY] >= current[Calendar.HOUR_OF_DAY]) {
                    DateType.NOW
                } else {
                    DateType.TODAY
                }
            }
            if (calendar[Calendar.DAY_OF_MONTH] + 1 == current[Calendar.DAY_OF_MONTH]) {
                return DateType.TOMORROW
            }
        }
    }

    return DateType.AFTER
}

fun String?.toDate(inputFormat: String = "yyyy-MM-dd'T'HH:mm:SS'Z'"): Date {
    val formatFrom = SimpleDateFormat(inputFormat, Locale.getDefault())

    return if (this == null) {
        Date()
    } else {
        formatFrom.parse(this) ?: Date()
    }
}

enum class DateType {
    NOW, TODAY, TOMORROW, AFTER, UNKNOWN
}