package com.yveschiong.macrofit.activities

import android.os.Bundle
import com.yveschiong.macrofit.R
import kotlinx.android.synthetic.main.activity_add_nutrition_fact.*

class AddNutritionFactActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_nutrition_fact)
        add_button.setOnClickListener { }
    }
}