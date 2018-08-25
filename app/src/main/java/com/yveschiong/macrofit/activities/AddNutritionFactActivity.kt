package com.yveschiong.macrofit.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import com.yveschiong.macrofit.App
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.constants.Constants
import com.yveschiong.macrofit.contracts.AddNutritionFactViewContract
import com.yveschiong.macrofit.models.NutritionFact
import com.yveschiong.macrofit.models.Weight
import kotlinx.android.synthetic.main.activity_add_nutrition_fact.*
import javax.inject.Inject

class AddNutritionFactActivity : BaseActivity(), AddNutritionFactViewContract.View {

    @Inject
    lateinit var presenter: AddNutritionFactViewContract.Presenter<AddNutritionFactViewContract.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_nutrition_fact)

        App.graph.inject(this)

        presenter.onAttach(this)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Weight.UNITS)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        unitsSpinner.adapter = adapter

        add_button.setOnClickListener {
            val nameText = name.text.toString()
            val weightText = weight.text.toString()
            val unitText = unitsSpinner.selectedItem.toString()
            val caloriesText = calories.text.toString()
            val proteinText = protein.text.toString()
            val carbText = carb.text.toString()
            val fatText = fat.text.toString()

            // We want to validate all the fields at once so we can show
            // all the error messages at once
            val isFoodNameValid = presenter.validateFoodName(nameText)
            val isWeightValid = presenter.validateWeight(weightText)
            val isCaloriesValid = presenter.validateCalories(caloriesText)
            val isProteinValid = presenter.validateProtein(proteinText)
            val isCarbValid = presenter.validateCarb(carbText)
            val isFatValid = presenter.validateFat(fatText)

            if (isFoodNameValid && isWeightValid && isCaloriesValid
                && isProteinValid && isCarbValid && isFatValid) {
                // Validated the fields so we can create a nutrition fact
                val result = Intent()
                result.putExtra(Constants.RESULT_KEY,
                    NutritionFact(nameText, weightText.toFloat(), unitText, caloriesText.toFloat(),
                        proteinText.toFloat(), carbText.toFloat(), fatText.toFloat()))

                setResult(Activity.RESULT_OK, result)
                finish()
            }
        }
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun shouldShowFoodNameErrorMessage(show: Boolean) {
        nameLayout.error = if (show) getString(R.string.add_nutrition_fact_required_error_text) else null
    }

    override fun shouldShowWeightErrorMessage(show: Boolean) {
        weightLayout.error = if (show) getString(R.string.add_nutrition_fact_required_error_text) else null
    }

    override fun shouldShowCaloriesErrorMessage(show: Boolean) {
        caloriesLayout.error = if (show) getString(R.string.add_nutrition_fact_required_error_text) else null
    }

    override fun shouldShowProteinErrorMessage(show: Boolean) {
        proteinLayout.error = if (show) getString(R.string.add_nutrition_fact_required_error_text) else null
    }

    override fun shouldShowCarbErrorMessage(show: Boolean) {
        carbLayout.error = if (show) getString(R.string.add_nutrition_fact_required_error_text) else null
    }

    override fun shouldShowFatErrorMessage(show: Boolean) {
        fatLayout.error = if (show) getString(R.string.add_nutrition_fact_required_error_text) else null
    }
}