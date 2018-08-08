package com.yveschiong.macrofit.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.getNormalString(): String {
    return SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(this)
}