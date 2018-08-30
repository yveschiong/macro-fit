package com.yveschiong.macrofit.adapters.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.interfaces.OnAdapterViewClicked
import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.viewholders.FoodListViewHolder

class FoodListAdapter(
    private var foodList: List<Food>,
    private val listener: OnAdapterViewClicked.FoodView
) : RecyclerView.Adapter<FoodListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListViewHolder {
        val holder = FoodListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_food, parent, false))

        // Handle the click for the card
        holder.itemView.setOnClickListener {
            listener.onViewClicked(foodList[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: FoodListViewHolder, position: Int) {
        with(foodList[position]) {
            holder.foodNameTextView.text = name
            holder.amountTextView.text = holder.itemView.context.getString(R.string.food_amount_text, amount, unit)
            holder.caloriesTextView.text = holder.itemView.context.getString(R.string.calories_text, calories)
            holder.proteinTextView.text = holder.itemView.context.getString(R.string.protein_text, protein)
            holder.carbsTextView.text = holder.itemView.context.getString(R.string.carbs_text, carbs)
            holder.fatTextView.text = holder.itemView.context.getString(R.string.fat_text, fat)
        }
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun setData(foodList: List<Food>) {
        this.foodList = foodList
    }
}
