package com.yveschiong.macrofit.interfaces

import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.models.NutritionFact
import com.yveschiong.macrofit.network.search.SearchResult

interface OnAdapterViewClicked {
    interface NutritionFactView {
        fun onViewClicked(nutritionFact: NutritionFact)
    }

    interface FoodView {
        fun onViewClicked(food: Food)
    }

    interface SearchResultView {
        fun onViewClicked(searchResult: SearchResult)
    }
}