package com.yveschiong.macrofit.extensions

import android.content.Context
import android.content.Intent
import android.support.design.widget.Snackbar
import android.view.View
import com.yveschiong.macrofit.activities.BaseActivity

fun Context.launchActivity(clazz: Class<out BaseActivity>) {
    startActivity(Intent(this, clazz))
}

fun Context.makeSnackbar(view: View, text: String) {
    Snackbar.make(view, text, Snackbar.LENGTH_LONG).show()
}