package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.contracts.AddFoodViewContract
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

    override fun setWeight(nutritionFact: NutritionFact, weightAmount: Float) {
        // Calculate the scale first and then apply it to every other amount
        val scale = weightAmount / nutritionFact.amount
        view?.setCardData(nutritionFact.name, weightAmount, nutritionFact.unit,
            nutritionFact.calories * scale, nutritionFact.protein * scale,
            nutritionFact.carbs * scale, nutritionFact.fat * scale)
    }
}
