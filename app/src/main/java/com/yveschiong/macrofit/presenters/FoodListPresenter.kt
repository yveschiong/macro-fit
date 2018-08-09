package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.interfaces.FoodListViewInterface
import com.yveschiong.macrofit.models.Food

class FoodListPresenter(private val foodList: List<Food>) {

    fun populate(viewInterface: FoodListViewInterface, position: Int) {
        val food = foodList[position]
        viewInterface.setFoodName(food.name)
        viewInterface.setAmountText(food.amount)
        viewInterface.setCaloriesText(food.calories)
        viewInterface.setProteinText(food.protein)
        viewInterface.setCarbsText(food.carbs)
        viewInterface.setFatText(food.fat)
    }

    fun getItemCount(): Int {
        return foodList.size
    }
}
