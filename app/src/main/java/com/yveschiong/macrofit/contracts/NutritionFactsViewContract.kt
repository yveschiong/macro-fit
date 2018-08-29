package com.yveschiong.macrofit.contracts

import com.yveschiong.macrofit.interfaces.BasePresenter
import com.yveschiong.macrofit.interfaces.BaseView
import com.yveschiong.macrofit.models.NutritionFact

interface NutritionFactsViewContract {
    interface View : BaseView {
        fun showNutrition(nutritionList: List<NutritionFact>)
        fun showEditNutritionFactActivity(nutritionFact: NutritionFact)
    }

    interface Presenter<V: View> : BasePresenter<V> {
        fun fetchNutrition()
        fun onCardClicked(nutritionFact: NutritionFact)
    }
}