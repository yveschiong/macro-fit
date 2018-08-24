package com.yveschiong.macrofit.activities

import android.os.Bundle
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.contracts.AddNutritionFactViewContract
import kotlinx.android.synthetic.main.activity_add_nutrition_fact.*
import javax.inject.Inject

class AddNutritionFactActivity : BaseActivity(), AddNutritionFactViewContract.View {

    @Inject
    lateinit var presenter: AddNutritionFactViewContract.Presenter<AddNutritionFactViewContract.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_nutrition_fact)
        add_button.setOnClickListener { }
    }
}