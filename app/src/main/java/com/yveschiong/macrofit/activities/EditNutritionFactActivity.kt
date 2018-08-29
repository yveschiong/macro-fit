package com.yveschiong.macrofit.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.widget.ArrayAdapter
import com.yveschiong.macrofit.App
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.constants.Constants
import com.yveschiong.macrofit.constants.ResponseCode
import com.yveschiong.macrofit.contracts.EditNutritionFactViewContract
import com.yveschiong.macrofit.models.NutritionFact
import com.yveschiong.macrofit.models.Weight
import kotlinx.android.synthetic.main.activity_edit_nutrition_fact.*
import javax.inject.Inject

class EditNutritionFactActivity : BaseActivity(), EditNutritionFactViewContract.View {

    @Inject
    lateinit var presenter: EditNutritionFactViewContract.Presenter<EditNutritionFactViewContract.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_nutrition_fact)

        App.graph.inject(this)

        presenter.onAttach(this)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Weight.UNITS)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        unitsSpinner.adapter = adapter

        // We have to disable text input layout hint animations since we are programmatically
        // setting the text input edit texts' texts and don't want the hint to be animated
        enableTextInputLayoutHintAnimations(false)

        // Fill the fields with the passed in nutrition fact
        val nutritionFact = intent.extras.getParcelable<NutritionFact>(Constants.EXTRA_NUTRITION_FACT)
        name.setText(nutritionFact.name)
        weight.setText(nutritionFact.amount.toString())
        unitsSpinner.setSelection(Weight.UNITS.indexOf(nutritionFact.unit))
        calories.setText(nutritionFact.calories.toString())
        protein.setText(nutritionFact.protein.toString())
        carb.setText(nutritionFact.carbs.toString())
        fat.setText(nutritionFact.fat.toString())

        // We will need to enable the text input layout hint animations again so that it would
        // would like it normally does for regular input
        enableTextInputLayoutHintAnimations(true)

        edit_button.setOnClickListener {
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

            // Validate the fields so we can edit the nutrition fact
            if (isFoodNameValid && isWeightValid && isCaloriesValid
                && isProteinValid && isCarbValid && isFatValid) {
                // Get the nutrition fact from the extras and edit it
                nutritionFact.name = nameText
                nutritionFact.amount = weightText.toFloat()
                nutritionFact.unit = unitText
                nutritionFact.calories = caloriesText.toFloat()
                nutritionFact.protein = proteinText.toFloat()
                nutritionFact.carbs = carbText.toFloat()
                nutritionFact.fat = fatText.toFloat()

                // Pass back the edited nutrition fact
                val result = Intent()
                result.putExtra(Constants.RESULT_KEY, nutritionFact)

                setResult(Activity.RESULT_OK, result)
                finish()
            }
        }

        delete_button.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(R.string.delete_dialog_title)
                .setMessage(R.string.delete_dialog_message)
                .setPositiveButton(R.string.delete_dialog_yes) { _, _ ->
                    // Pass back the soon to be deleted nutrition fact
                    val result = Intent()
                    result.putExtra(Constants.RESULT_KEY, nutritionFact)
                    // Add a flag to show that we want to delete this nutrition fact
                    result.putExtra(Constants.EXTRA_SHOULD_DELETE, true)

                    setResult(Activity.RESULT_OK, result)
                    finish()
                }
                .setNegativeButton(R.string.delete_dialog_no) { _, _ ->
                    // Do nothing
                }
                .create()
                .show()
        }
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    private fun enableTextInputLayoutHintAnimations(enabled: Boolean) {
        nameLayout.isHintAnimationEnabled = enabled
        weightLayout.isHintAnimationEnabled = enabled
        caloriesLayout.isHintAnimationEnabled = enabled
        proteinLayout.isHintAnimationEnabled = enabled
        carbLayout.isHintAnimationEnabled = enabled
        fatLayout.isHintAnimationEnabled = enabled
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