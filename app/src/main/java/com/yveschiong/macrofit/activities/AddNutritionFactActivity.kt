package com.yveschiong.macrofit.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.widget.ArrayAdapter
import com.yveschiong.macrofit.App
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.constants.Constants
import com.yveschiong.macrofit.constants.ResponseCode
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

    private fun tryShowError(layout: TextInputLayout, code: Int) {
        when (code) {
            ResponseCode.FIELD_IS_REQUIRED -> {
                layout.error = getString(R.string.field_required_error_text)
            }
            ResponseCode.FIELD_IS_INVALID -> {
                layout.error = getString(R.string.field_invalid_error_text)
            }
            ResponseCode.OK -> {
                layout.error = null
            }
        }
    }

    override fun tryShowFoodNameErrorMessage(code: Int) {
        tryShowError(nameLayout, code)
    }

    override fun tryShowWeightErrorMessage(code: Int) {
        tryShowError(weightLayout, code)
    }

    override fun tryShowCaloriesErrorMessage(code: Int) {
        tryShowError(caloriesLayout, code)
    }

    override fun tryShowProteinErrorMessage(code: Int) {
        tryShowError(proteinLayout, code)
    }

    override fun tryShowCarbErrorMessage(code: Int) {
        tryShowError(carbLayout, code)
    }

    override fun tryShowFatErrorMessage(code: Int) {
        tryShowError(fatLayout, code)
    }
}