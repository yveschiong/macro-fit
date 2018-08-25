package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.contracts.AddNutritionFactViewContract
import javax.inject.Inject

class AddNutritionFactPresenter<V : AddNutritionFactViewContract.View> @Inject constructor(

) : RootPresenter<V>(), AddNutritionFactViewContract.Presenter<V> {

    override fun validateFoodName(input: String): Boolean {
        val showErrorMessage = input.isEmpty()
        view?.shouldShowFoodNameErrorMessage(showErrorMessage)
        return !showErrorMessage
    }

    override fun validateWeight(input: String): Boolean {
        val showErrorMessage = input.isEmpty()
        view?.shouldShowWeightErrorMessage(showErrorMessage)
        return !showErrorMessage
    }

    override fun validateCalories(input: String): Boolean {
        val showErrorMessage = input.isEmpty()
        view?.shouldShowCaloriesErrorMessage(showErrorMessage)
        return !showErrorMessage
    }

    override fun validateProtein(input: String): Boolean {
        val showErrorMessage = input.isEmpty()
        view?.shouldShowProteinErrorMessage(showErrorMessage)
        return !showErrorMessage
    }

    override fun validateCarb(input: String): Boolean {
        val showErrorMessage = input.isEmpty()
        view?.shouldShowCarbErrorMessage(showErrorMessage)
        return !showErrorMessage
    }

    override fun validateFat(input: String): Boolean {
        val showErrorMessage = input.isEmpty()
        view?.shouldShowFatErrorMessage(showErrorMessage)
        return !showErrorMessage
    }
}
