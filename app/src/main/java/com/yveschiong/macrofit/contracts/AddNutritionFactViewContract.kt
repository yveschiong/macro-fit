package com.yveschiong.macrofit.contracts

import com.yveschiong.macrofit.interfaces.BasePresenter
import com.yveschiong.macrofit.interfaces.BaseView

interface AddNutritionFactViewContract {
    interface View : BaseView {
        fun tryShowFoodNameErrorMessage(code: Int)
        fun tryShowWeightErrorMessage(code: Int)
        fun tryShowCaloriesErrorMessage(code: Int)
        fun tryShowProteinErrorMessage(code: Int)
        fun tryShowCarbErrorMessage(code: Int)
        fun tryShowFatErrorMessage(code: Int)
    }

    interface Presenter<V: View> : BasePresenter<V> {
        // Food name validation is done differently due to the need
        // to check for name uniqueness which requires a fetch
        fun validateFoodName(input: String, func: (Boolean) -> Unit)

        // If the field is valid then return true, false otherwise
        fun validateWeight(input: String): Boolean
        fun validateCalories(input: String): Boolean
        fun validateProtein(input: String): Boolean
        fun validateCarb(input: String): Boolean
        fun validateFat(input: String): Boolean
    }
}