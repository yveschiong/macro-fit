package com.yveschiong.macrofit.contracts

import com.yveschiong.macrofit.interfaces.BasePresenter
import com.yveschiong.macrofit.interfaces.BaseView
import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.models.NutritionFact
import java.util.*

interface MainViewContract {
    interface View : BaseView {
        fun toggleExpandedAppBar()
        fun toggleCalendarExpansion()
        fun setMonthViewDay(day: Calendar)
        fun setMonthViewMonth(month: Calendar)
        fun setTitleText(text: String)
        fun setNavViewCheckedItem(id: Int)
        fun setActionBarState(id: Int)
        fun setViewStates(id: Int)
        fun switchToFragment(id: Int)
        fun invalidateActionBarMenu(id: Int)
        fun showActivity(id: Int?)
        fun showTotalMacroInfo(calories: Int, protein: Int, carbs: Int, fat: Int,
                               proteinSplit: Int, carbsSplit: Int, fatSplit: Int)
    }

    interface Presenter<V: View> : BasePresenter<V> {
        fun onCalendarIconSelected()
        fun setMenuNavigation(id: Int)
        fun changeMonthViewSelectedDay(day: Calendar)
        fun incrementMonthViewMonthBy(month: Calendar, increment: Int)
        fun onMonthViewDaySelected(day: Calendar)
        fun onFabClickedFrom(id: Int?)
        fun addNutritionFact(nutritionFact: NutritionFact)
        fun editNutritionFact(nutritionFact: NutritionFact)
        fun deleteNutritionFact(nutritionFact: NutritionFact)
        fun addFood(food: Food)
        fun editFood(food: Food)
        fun deleteFood(food: Food)
        fun fetchTotalMacroInfo(day: Calendar)
        fun search(query: String?)
    }
}