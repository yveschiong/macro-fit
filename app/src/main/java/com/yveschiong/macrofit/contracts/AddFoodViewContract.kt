package com.yveschiong.macrofit.contracts

import com.yveschiong.macrofit.interfaces.BasePresenter
import com.yveschiong.macrofit.interfaces.BaseView
import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.models.NutritionFact
import com.yveschiong.macrofit.models.Weight

interface AddFoodViewContract {
    interface View : BaseView {
        fun showNutritionFacts(nutritionFacts: List<NutritionFact>)
        fun setUnitText(text: String)
        fun tryShowWeightErrorMessage(code: Int)
        fun setCardData(name: String,
                        amount: Float,
                        @Weight.Unit
                        unit: String,
                        calories: Float,
                        protein: Float,
                        carbs: Float,
                        fat: Float
        )
    }

    interface Presenter<V: View> : BasePresenter<V> {
        fun fetchNutritionFacts()
        fun selectNutritionFact(nutritionFact: NutritionFact)
        fun validateWeight(input: String): Boolean
        fun setWeight(nutritionFact: NutritionFact, weightAmount: Float)
        fun createFood(timeAdded: Long, nutritionFact: NutritionFact, weightAmount: Float): Food
    }
}