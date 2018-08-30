package com.yveschiong.macrofit.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import com.yveschiong.macrofit.App
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.adapters.array.NutritionFactsSpinner
import com.yveschiong.macrofit.constants.Constants
import com.yveschiong.macrofit.constants.ResponseCode
import com.yveschiong.macrofit.contracts.EditFoodViewContract
import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.models.NutritionFact
import com.yveschiong.macrofit.models.Weight
import kotlinx.android.synthetic.main.activity_edit_food.*
import kotlinx.android.synthetic.main.list_item_food.*
import javax.inject.Inject

class EditFoodActivity : BaseActivity(), EditFoodViewContract.View {

    @Inject
    lateinit var presenter: EditFoodViewContract.Presenter<EditFoodViewContract.View>

    private var food: Food = Food()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_food)

        App.graph.inject(this)

        presenter.onAttach(this)

        // We have to disable text input layout hint animations since we are programmatically
        // setting the text input edit texts' texts and don't want the hint to be animated
        enableTextInputLayoutHintAnimations(false)

        food = intent.extras.getParcelable(Constants.EXTRA_FOOD)
        weight.setText(food.amount.toString())

        // We will need to enable the text input layout hint animations again so that it would
        // would like it normally does for regular input
        enableTextInputLayoutHintAnimations(true)

        presenter.fetchNutritionFacts()

        // Set the spinner item selected listener to respond accordingly
        nutritionFactSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing for now
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                parent?.adapter?.getItem(position)?.let {
                    if (it is NutritionFact) {
                        presenter.selectNutritionFact(it)
                    }
                }

                // Updates the card on any spinner item selection
                setWeight(weight.text.toString())
            }
        }

        weight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Do nothing for now
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing for now
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setWeight(s.toString())
            }
        })

        edit_button.setOnClickListener { _ ->
            val weightText = weight.text.toString()
            val isWeightValid = presenter.validateWeight(weightText)

            if (isWeightValid) {
                // Validated the fields so we can edit the food
                val result = Intent()
                nutritionFactSpinner.selectedItem.let {
                    if (it !is NutritionFact) {
                        setResult(Activity.RESULT_CANCELED)
                        finish()
                        return@setOnClickListener
                    }

                    presenter.modifyFood(food, it, presenter.parseWeightText(weightText))

                    // Pass back the edited food
                    result.putExtra(Constants.RESULT_KEY, food)
                }

                setResult(Activity.RESULT_OK, result)
                finish()
            }
        }

        delete_button.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(R.string.delete_dialog_title)
                .setMessage(R.string.delete_dialog_message)
                .setPositiveButton(R.string.delete_dialog_yes) { _, _ ->
                    // Pass back the soon to be deleted food
                    val result = Intent()
                    result.putExtra(Constants.RESULT_KEY, food)
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
        weightLayout.isHintAnimationEnabled = enabled
    }

    private fun setWeight(text: String?) {
        nutritionFactSpinner.selectedItem.let {
            if (it is NutritionFact) {
                presenter.setWeight(it, presenter.parseWeightText(text))
            }
        }
    }

    override fun showNutritionFacts(nutritionFacts: List<NutritionFact>) {
        val adapter = NutritionFactsSpinner(this, android.R.layout.simple_spinner_item, nutritionFacts)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        nutritionFactSpinner.adapter = adapter

        // Select the food's nutrition fact
        presenter.selectNutritionFact(food, nutritionFacts)

        // We want to set the food's weight so that the card can be populated
        setWeight(weight.text.toString())
    }

    override fun setSelectedNutritionFactPosition(position: Int) {
        nutritionFactSpinner.setSelection(position)
    }

    override fun setUnitText(text: String) {
        unitText.text = text
    }

    override fun tryShowWeightErrorMessage(code: Int) {
        when (code) {
            ResponseCode.FIELD_IS_REQUIRED -> {
                weightLayout.error = getString(R.string.field_required_error_text)
            }
            ResponseCode.FIELD_IS_INVALID -> {
                weightLayout.error = getString(R.string.field_invalid_error_text)
            }
            ResponseCode.OK -> {
                weightLayout.error = null
            }
        }
    }

    override fun setCardData(name: String,
                             amount: Float,
                             @Weight.Unit unit: String,
                             calories: Float,
                             protein: Float,
                             carbs: Float,
                             fat: Float) {
        this.name.text = name
        this.amount.text = getString(R.string.food_amount_text, amount, unit)
        this.calories.text = getString(R.string.calories_text, calories)
        this.protein.text = getString(R.string.protein_text, protein)
        this.carbs.text = getString(R.string.carbs_text, carbs)
        this.fat.text = getString(R.string.fat_text, fat)
    }
}