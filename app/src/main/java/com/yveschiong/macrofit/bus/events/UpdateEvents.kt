package com.yveschiong.macrofit.bus.events

import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.models.NutritionFact

class UpdateEvents {
    open class NutritionFactEvent(val nutritionFact: NutritionFact): Event()
    class AddedNutritionFactEvent(nutritionFact: NutritionFact) : NutritionFactEvent(nutritionFact)
    class EditedNutritionFactEvent(nutritionFact: NutritionFact) : NutritionFactEvent(nutritionFact)
    class DeletedNutritionFactEvent(nutritionFact: NutritionFact) : NutritionFactEvent(nutritionFact)

    open class FoodEvent(val food: Food): Event()
    class AddedFoodEvent(food: Food) : FoodEvent(food)
    class EditedFoodEvent(food: Food) : FoodEvent(food)
    class DeletedFoodEvent(food: Food) : FoodEvent(food)
}