package com.yveschiong.macrofit.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.yveschiong.macrofit.R

class NutritionFactsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val foodNameTextView: TextView
    val amountTextView: TextView
    val caloriesTextView: TextView
    val proteinTextView: TextView
    val carbsTextView: TextView
    val fatTextView: TextView

    init {
        foodNameTextView = itemView.findViewById(R.id.name)
        amountTextView = itemView.findViewById(R.id.amount)
        caloriesTextView = itemView.findViewById(R.id.calories)
        proteinTextView = itemView.findViewById(R.id.protein)
        carbsTextView = itemView.findViewById(R.id.carbs)
        fatTextView = itemView.findViewById(R.id.fat)
    }
}
