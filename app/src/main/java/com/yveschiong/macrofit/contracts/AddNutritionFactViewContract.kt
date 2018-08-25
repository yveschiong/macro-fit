package com.yveschiong.macrofit.contracts

import com.yveschiong.macrofit.interfaces.BasePresenter
import com.yveschiong.macrofit.interfaces.BaseView

interface AddNutritionFactViewContract {
    interface View : BaseView {
        fun shouldShowFoodNameErrorMessage(show: Boolean)
        fun shouldShowWeightErrorMessage(show: Boolean)
        fun shouldShowCaloriesErrorMessage(show: Boolean)
        fun shouldShowProteinErrorMessage(show: Boolean)
        fun shouldShowCarbErrorMessage(show: Boolean)
        fun shouldShowFatErrorMessage(show: Boolean)
    }

    interface Presenter<V: View> : BasePresenter<V> {
        // If the field is valid then return true, false otherwise
        fun validateFoodName(input: String): Boolean
        fun validateWeight(input: String): Boolean
        fun validateCalories(input: String): Boolean
        fun validateProtein(input: String): Boolean
        fun validateCarb(input: String): Boolean
        fun validateFat(input: String): Boolean
    }
}