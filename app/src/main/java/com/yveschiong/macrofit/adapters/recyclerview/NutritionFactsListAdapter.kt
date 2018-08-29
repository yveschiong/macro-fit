package com.yveschiong.macrofit.adapters.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.interfaces.OnAdapterViewClicked
import com.yveschiong.macrofit.models.NutritionFact
import com.yveschiong.macrofit.viewholders.NutritionFactsListViewHolder

class NutritionFactsListAdapter(
    private var nutritionFactsList: List<NutritionFact>,
    private val listener: OnAdapterViewClicked.NutritionFactView
) : RecyclerView.Adapter<NutritionFactsListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutritionFactsListViewHolder {
        val holder = NutritionFactsListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_food, parent, false))

        // Handle the click for the card
        holder.itemView.setOnClickListener {
            listener.onViewClicked(nutritionFactsList[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: NutritionFactsListViewHolder, position: Int) {
        with(nutritionFactsList[position]) {
            holder.foodNameTextView.text = name
            holder.amountTextView.text = holder.itemView.context.getString(R.string.nutrition_fact_amount_text, amount, unit)
            holder.caloriesTextView.text = holder.itemView.context.getString(R.string.calories_text, calories)
            holder.proteinTextView.text = holder.itemView.context.getString(R.string.protein_text, protein)
            holder.carbsTextView.text = holder.itemView.context.getString(R.string.carbs_text, carbs)
            holder.fatTextView.text = holder.itemView.context.getString(R.string.fat_text, fat)
        }
    }

    override fun getItemCount(): Int {
        return nutritionFactsList.size
    }

    fun setData(nutritionFactsList: List<NutritionFact>) {
        this.nutritionFactsList = nutritionFactsList
    }
}