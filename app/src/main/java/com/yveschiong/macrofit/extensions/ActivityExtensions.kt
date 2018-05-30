package com.yveschiong.macrofit.extensions

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.replaceFragment(containerId: Int, fragment: Fragment) {
    supportFragmentManager.inTransaction{ replace(containerId, fragment) }
}