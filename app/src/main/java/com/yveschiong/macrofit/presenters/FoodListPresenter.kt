package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.interfaces.FoodListViewInterface
import com.yveschiong.macrofit.models.Food

class FoodListPresenter(private val foodList: List<Food>) {

    fun populate(viewInterface: FoodListViewInterface, position: Int) {
        with(foodList[position]) {
            viewInterface.setFoodName(name)
            viewInterface.setAmountText(amount)
            viewInterface.setCaloriesText(calories)
            viewInterface.setProteinText(protein)
            viewInterface.setCarbsText(carbs)
            viewInterface.setFatText(fat)
        }
    }

    fun getItemCount(): Int {
        return foodList.size
    }
}
