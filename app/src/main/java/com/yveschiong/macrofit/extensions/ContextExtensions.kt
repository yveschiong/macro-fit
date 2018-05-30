package com.yveschiong.macrofit.extensions

import android.content.Context
import android.content.Intent
import com.yveschiong.macrofit.activities.BaseActivity

fun Context.launchActivity(clazz: Class<out BaseActivity>) {
    startActivity(Intent(this, clazz))
}