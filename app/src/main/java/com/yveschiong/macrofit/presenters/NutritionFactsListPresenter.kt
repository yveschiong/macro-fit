package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.interfaces.NutritionFactsListViewInterface
import com.yveschiong.macrofit.models.NutritionFact

class NutritionFactsListPresenter(private val nutritionFactsList: List<NutritionFact>) {

    fun populate(viewInterface: NutritionFactsListViewInterface, position: Int) {
        with(nutritionFactsList[position]) {
            viewInterface.setFoodName(name)
            viewInterface.setAmountText(amount, unit)
            viewInterface.setCaloriesText(calories)
            viewInterface.setProteinText(protein)
            viewInterface.setCarbsText(carbs)
            viewInterface.setFatText(fat)
        }
    }

    fun getItemCount(): Int {
        return nutritionFactsList.size
    }
}
