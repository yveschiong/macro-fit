package com.yveschiong.macrofit.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.contracts.AddNutritionFactViewContract
import com.yveschiong.macrofit.models.Weight
import kotlinx.android.synthetic.main.activity_add_nutrition_fact.*
import javax.inject.Inject

class AddNutritionFactActivity : BaseActivity(), AddNutritionFactViewContract.View {

    @Inject
    lateinit var presenter: AddNutritionFactViewContract.Presenter<AddNutritionFactViewContract.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_nutrition_fact)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Weight.UNITS)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        unitsSpinner.adapter = adapter

        add_button.setOnClickListener { }
    }
}