package com.cobasendiri.youmov.ui.util

import android.os.Build
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

fun String.toReadableDate(): String {
    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return this
    return try {
        val date = LocalDate.parse(this)
        val formatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.getDefault())
        date.format(formatter)
    } catch (e: DateTimeParseException) {
        this
    }
}

fun String.toReadableDateTime(): String {
    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return this
    val formatter = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm", Locale.getDefault())
    return try {
        val instant = Instant.parse(this)
        instant.atZone(ZoneId.systemDefault()).format(formatter)
    } catch (e1: DateTimeParseException) {
        this.toReadableDate()
    }
}