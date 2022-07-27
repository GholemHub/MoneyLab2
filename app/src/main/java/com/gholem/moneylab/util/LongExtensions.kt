package com.gholem.moneylab.util

import android.icu.text.SimpleDateFormat
import java.util.*

fun Long.timestampToString(): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy")
    return sdf.format(Date(this))
}