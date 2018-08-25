package com.yveschiong.macrofit.bus.events

import com.yveschiong.macrofit.models.NutritionFact

class UpdateEvents {
    class AddedNutritionFactEvent(val nutritionFact: NutritionFact): Event()
}