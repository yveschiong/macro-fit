package com.yveschiong.macrofit.views

import android.content.Context
import android.support.design.widget.TextInputLayout
import android.util.AttributeSet


class TextInputLayoutBaselineAdjusted : TextInputLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getBaseline(): Int {
        val editText = editText

        // If the edit text is not null then return its baseline
        editText?.let {
            return editText.paddingTop + editText.baseline
        }

        return super.getBaseline()
    }
}