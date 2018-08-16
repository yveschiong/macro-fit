package com.yveschiong.macrofit.models

import android.support.annotation.StringDef

class Weight {
    @Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION,
        AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.EXPRESSION)
    @Retention(AnnotationRetention.BINARY)
    @StringDef(GRAMS, PIECES)
    annotation class Unit

    companion object {
        const val GRAMS = "g"
        const val PIECES = "pcs"
    }
}