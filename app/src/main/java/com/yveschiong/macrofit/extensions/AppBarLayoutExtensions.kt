package com.yveschiong.macrofit.extensions

import android.support.design.widget.AppBarLayout

fun AppBarLayout.isExpanded(): Boolean {
    return height - bottom == 0
}