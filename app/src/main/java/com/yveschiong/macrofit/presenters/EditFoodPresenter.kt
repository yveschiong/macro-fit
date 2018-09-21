package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.constants.ResponseCode
import com.yveschiong.macrofit.contracts.EditFoodViewContract
import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.models.NutritionFact
import com.yveschiong.macrofit.repositories.NutritionFactsRepository
import javax.inject.Inject

class EditFoodPresenter<V : EditFoodViewContract.View> @Inject constructor(
    private val nutritionFactsRepository: NutritionFactsRepository
) : RootPresenter<V>(), EditFoodViewContract.Presenter<V> {

    override fun fetchNutritionFacts() {
        nutritionFactsRepository.getNutritionFacts()
            .call { view?.showNutritionFacts(it) }
    }

    override fun selectNutritionFact(nutritionFact: NutritionFact) {
        view?.setUnitText(nutritionFact.unit)
    }

    override fun selectNutritionFact(food: Food, nutritionFacts: List<NutritionFact>) {
        for (i in nutritionFacts.indices) {
            if (nutritionFacts[i].id == food.factId) {
                view?.setUnitText(nutritionFacts[i].unit)
                view?.setSelectedNutritionFactPosition(i)
            }
        }
    }

    override fun validateWeight(input: String): Boolean {
        // Check if the input is not empty and if the value is greater than 0
        when {
            input.isEmpty() -> view?.tryShowWeightErrorMessage(ResponseCode.FIELD_IS_REQUIRED)
            (input.toFloatOrNull() ?: 0.0f) <= 0.0f -> view?.tryShowWeightErrorMessage(ResponseCode.FIELD_IS_INVALID)
            else -> {
                view?.tryShowWeightErrorMessage(ResponseCode.OK)
                return true
            }
        }

        return false
    }

    override fun parseWeightText(text: String?): Float {
        return text?.toFloatOrNull() ?: 0.0f
    }

    override fun setWeight(nutritionFact: NutritionFact, weightAmount: Float) {
        // Calculate the scale first and then apply it to every other amount
        val scale = weightAmount / nutritionFact.amount
        view?.setCardData(nutritionFact.name, weightAmount, nutritionFact.unit,
            nutritionFact.calories * scale, nutritionFact.protein * scale,
            nutritionFact.carbs * scale, nutritionFact.fat * scale)
    }

    override fun createFood(timeAdded: Long, nutritionFact: NutritionFact, weightAmount: Float): Food {
        // Calculate the scale first and then apply it to every other amount
        val scale = weightAmount / nutritionFact.amount
        return Food(timeAdded, nutritionFact.name, weightAmount, nutritionFact.unit,
            nutritionFact.calories * scale, nutritionFact.protein * scale,
            nutritionFact.carbs * scale, nutritionFact.fat * scale, nutritionFact.id)
    }

    override fun modifyFood(food: Food, nutritionFact: NutritionFact, weightAmount: Float) {
        // Calculate the scale first and then apply it to every other amount
        val scale = weightAmount / nutritionFact.amount

        // Modify the given food parameter
        food.name = nutritionFact.name
        food.amount = weightAmount
        food.unit = nutritionFact.unit
        food.calories = nutritionFact.calories * scale
        food.protein = nutritionFact.protein * scale
        food.carbs = nutritionFact.carbs * scale
        food.fat = nutritionFact.fat * scale
        food.factId = nutritionFact.id
    }
}
