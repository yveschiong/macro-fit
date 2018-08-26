package com.yveschiong.macrofit.adapters.array

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.yveschiong.macrofit.models.NutritionFact


// We want to create a custom spinner adapter so we can use a list of nutrition fact objects
// instead of just using strings. This way we can interact with the object upon selection and
// easily get the nutrition fact properties.
class NutritionFactsSpinner(
    context: Context,
    textViewResourceId: Int,
    private val nutritionFacts: List<NutritionFact>) : ArrayAdapter<NutritionFact>(context, textViewResourceId, nutritionFacts) {

    override fun getCount(): Int {
        return nutritionFacts.size
    }

    override fun getItem(position: Int): NutritionFact? {
        return nutritionFacts[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val label = super.getView(position, convertView, parent) as TextView

        // Set the text to be the nutrition fact name
        label.text = nutritionFacts[position].name

        return label
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val label = super.getDropDownView(position, convertView, parent) as TextView

        // Set the text to be the nutrition fact name
        label.text = nutritionFacts[position].name

        return label
    }
}