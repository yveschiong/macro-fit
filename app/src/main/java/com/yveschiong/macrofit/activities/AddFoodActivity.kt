package com.yveschiong.macrofit.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import com.yveschiong.macrofit.App
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.adapters.array.NutritionFactsSpinner
import com.yveschiong.macrofit.contracts.AddFoodViewContract
import com.yveschiong.macrofit.models.NutritionFact
import com.yveschiong.macrofit.models.Weight
import kotlinx.android.synthetic.main.activity_add_food.*
import kotlinx.android.synthetic.main.list_item_food.*
import javax.inject.Inject

class AddFoodActivity : BaseActivity(), AddFoodViewContract.View {

    @Inject
    lateinit var presenter: AddFoodViewContract.Presenter<AddFoodViewContract.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)

        App.graph.inject(this)

        presenter.onAttach(this)

        presenter.fetchNutritionFacts()

        // We want to hide the edit icon since this is just a preview
        editWrapper.visibility = View.GONE

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
                setWeight((s ?: "").toString())
            }
        })
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    private fun setWeight(text: String?) {
        nutritionFactSpinner.selectedItem.let {
            if (it is NutritionFact) {
                if (text.isNullOrEmpty()) {
                    // Sets the weight to 0 if there is no text in the edit text
                    presenter.setWeight(it, 0.0f)
                } else {
                    presenter.setWeight(it, text.toString().toFloat())
                }
            }
        }
    }

    override fun showNutritionFacts(nutritionFacts: List<NutritionFact>) {
        val adapter = NutritionFactsSpinner(this, android.R.layout.simple_spinner_item, nutritionFacts)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        nutritionFactSpinner.adapter = adapter

        // Select the default nutrition fact
        nutritionFactSpinner.selectedItem.let {
            if (it is NutritionFact) {
                presenter.selectNutritionFact(it)
            }
        }

        // We want to set an initial weight so that the card can be populated
        setWeight(weight.text.toString())
    }

    override fun setUnitText(text: String) {
        unitText.text = text
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