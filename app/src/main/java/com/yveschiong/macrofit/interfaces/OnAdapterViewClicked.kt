package com.yveschiong.macrofit.interfaces

import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.models.NutritionFact

interface OnAdapterViewClicked {
    interface NutritionFactView {
        fun onViewClicked(nutritionFact: NutritionFact)
    }

    interface FoodView {
        fun onViewClicked(food: Food)
    }
}