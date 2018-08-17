package com.yveschiong.macrofit.interfaces

interface NutritionFactsListViewInterface {
    fun setFoodName(name: String)
    fun setAmountText(amount: Float, unit: String)
    fun setCaloriesText(calories: Float)
    fun setProteinText(protein: Float)
    fun setCarbsText(carbs: Float)
    fun setFatText(fat: Float)
}
