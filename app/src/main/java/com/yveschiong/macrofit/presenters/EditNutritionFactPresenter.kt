package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.constants.ResponseCode
import com.yveschiong.macrofit.contracts.EditNutritionFactViewContract
import javax.inject.Inject

class EditNutritionFactPresenter<V : EditNutritionFactViewContract.View> @Inject constructor()
    : RootPresenter<V>(), EditNutritionFactViewContract.Presenter<V> {

    // Helper method for easily checking all fields for errors
    private fun validate(required: Boolean, invalid: Boolean, func: (Int) -> Unit): Boolean {
        // Check if the input is not empty and if the value is greater than 0
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

    override fun validateFoodName(input: String): Boolean {
        return validate(input.isEmpty(), false) {
            view?.tryShowFoodNameErrorMessage(it)
        }
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
