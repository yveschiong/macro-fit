package com.yveschiong.macrofit.contracts

import com.yveschiong.macrofit.interfaces.BasePresenter
import com.yveschiong.macrofit.interfaces.BaseView
import com.yveschiong.macrofit.models.NutritionFact

interface NutritionViewContract {
    interface View : BaseView {
        fun showNutrition(nutritionList: List<NutritionFact>)
    }

    interface Presenter<V: View> : BasePresenter<V> {
        fun fetchNutrition()
    }
}