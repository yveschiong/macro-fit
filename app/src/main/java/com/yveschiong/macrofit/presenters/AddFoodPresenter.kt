package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.constants.ResponseCode
import com.yveschiong.macrofit.contracts.AddFoodViewContract
import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.models.NutritionFact
import com.yveschiong.macrofit.repositories.NutritionFactsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddFoodPresenter<V : AddFoodViewContract.View> @Inject constructor(
    private val nutritionFactsRepository: NutritionFactsRepository
) : RootPresenter<V>(), AddFoodViewContract.Presenter<V> {

    override fun fetchNutritionFacts() {
        nutritionFactsRepository.getNutritionFacts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { view?.showNutritionFacts(it) }
            .addToDisposables()
    }

    override fun selectNutritionFact(nutritionFact: NutritionFact) {
        view?.setUnitText(nutritionFact.unit)
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
            nutritionFact.carbs * scale, nutritionFact.fat * scale)
    }
}
