package com.yveschiong.macrofit.bus.events

import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.models.NutritionFact

class UpdateEvents {
    class AddedNutritionFactEvent(val nutritionFact: NutritionFact): Event()
    class EditedNutritionFactEvent(val nutritionFact: NutritionFact): Event()
    class DeletedNutritionFactEvent(val nutritionFact: NutritionFact): Event()
    class AddedFoodEvent(val food: Food): Event()
}