package com.yveschiong.macrofit.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.interfaces.NutritionFactsListViewInterface

class NutritionFactsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), NutritionFactsListViewInterface {

    private val foodNameTextView: TextView
    private val amountTextView: TextView
    private val caloriesTextView: TextView
    private val proteinTextView: TextView
    private val carbsTextView: TextView
    private val fatTextView: TextView

    init {
        foodNameTextView = itemView.findViewById(R.id.name)
        amountTextView = itemView.findViewById(R.id.amount)
        caloriesTextView = itemView.findViewById(R.id.calories)
        proteinTextView = itemView.findViewById(R.id.protein)
        carbsTextView = itemView.findViewById(R.id.carbs)
        fatTextView = itemView.findViewById(R.id.fat)
    }

    override fun setFoodName(name: String) {
        foodNameTextView.text = name
    }

    override fun setAmountText(amount: Float, unit: String) {
        amountTextView.text = itemView.context.getString(R.string.nutrition_fact_amount_text, amount, unit)
    }

    override fun setCaloriesText(calories: Float) {
        caloriesTextView.text = itemView.context.getString(R.string.calories_text, calories)
    }

    override fun setProteinText(protein: Float) {
        proteinTextView.text = itemView.context.getString(R.string.protein_text, protein)
    }

    override fun setCarbsText(carbs: Float) {
        carbsTextView.text = itemView.context.getString(R.string.carbs_text, carbs)
    }

    override fun setFatText(fat: Float) {
        fatTextView.text = itemView.context.getString(R.string.fat_text, fat)
    }
}
