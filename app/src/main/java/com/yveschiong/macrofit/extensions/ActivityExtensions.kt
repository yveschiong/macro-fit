package com.yveschiong.macrofit.extensions

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.yveschiong.macrofit.activities.BaseActivity

fun AppCompatActivity.replaceFragment(containerId: Int, fragment: Fragment, tag: String) {
    supportFragmentManager.inTransaction{ replace(containerId, fragment, tag) }
}

fun AppCompatActivity.launchActivityForResult(clazz: Class<out BaseActivity>, requestCode: Int) {
    startActivityForResult(Intent(this, clazz), requestCode)
}