package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.constants.ResponseCode
import com.yveschiong.macrofit.contracts.AddNutritionFactViewContract
import com.yveschiong.macrofit.repositories.NutritionFactsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddNutritionFactPresenter<V : AddNutritionFactViewContract.View> @Inject constructor(
    private val nutritionFactsRepository: NutritionFactsRepository
) : RootPresenter<V>(), AddNutritionFactViewContract.Presenter<V> {

    // Helper method for easily checking all fields for errors
    private fun validate(required: Boolean, invalid: Boolean, func: (Int) -> Unit): Boolean {
        when {
            required -> func(ResponseCode.FIELD_IS_REQUIRED)
            invalid -> func(ResponseCode.FIELD_IS_INVALID)
            else -> {
                func(ResponseCode.OK)
                return true
            }
        }

        return false
    }

    override fun validateFoodName(input: String, successFunc: () -> Unit) {
        if (!validate(input.isEmpty(), false) { view?.tryShowFoodNameErrorMessage(it) }) {
            // If the simple validation fails then skip the uniqueness check
            return
        }

        // Check the database to see if the name already exists
        nutritionFactsRepository.alreadyExists(input)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it) {
                    // Show an error message for the value being a duplicate
                    view?.tryShowFoodNameErrorMessage(ResponseCode.FIELD_VALUE_ALREADY_EXISTS)
                } else {
                    // The name does not exist so it passes the uniqueness check
                    successFunc()
                }
            }
            .addToDisposables()
    }

    override fun validateWeight(input: String): Boolean {
        return validate(input.isEmpty(), (input.toFloatOrNull() ?: 0.0f) <= 0.0f) {
            view?.tryShowWeightErrorMessage(it)
        }
    }

    override fun validateCalories(input: String): Boolean {
        return validate(input.isEmpty(), (input.toFloatOrNull() ?: 0.0f) <= 0.0f) {
            view?.tryShowCaloriesErrorMessage(it)
        }
    }

    override fun validateProtein(input: String): Boolean {
        return validate(input.isEmpty(), (input.toFloatOrNull() ?: 0.0f) <= 0.0f) {
            view?.tryShowProteinErrorMessage(it)
        }
    }

    override fun validateCarb(input: String): Boolean {
        return validate(input.isEmpty(), (input.toFloatOrNull() ?: 0.0f) <= 0.0f) {
            view?.tryShowCarbErrorMessage(it)
        }
    }

    override fun validateFat(input: String): Boolean {
        return validate(input.isEmpty(), (input.toFloatOrNull() ?: 0.0f) <= 0.0f) {
            view?.tryShowFatErrorMessage(it)
        }
    }
}
