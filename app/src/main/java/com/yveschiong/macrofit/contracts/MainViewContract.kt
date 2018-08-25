package com.yveschiong.macrofit.contracts

import com.yveschiong.macrofit.interfaces.BasePresenter
import com.yveschiong.macrofit.interfaces.BaseView
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
        fun switchToFragment(id: Int)
        fun showActivity(id: Int?)
    }

    interface Presenter<V: View> : BasePresenter<V> {
        fun onCalendarIconSelected()
        fun setMenuNavigation(id: Int)
        fun changeMonthViewSelectedDay(day: Calendar)
        fun incrementMonthViewMonthBy(month: Calendar, increment: Int)
        fun onMonthViewDaySelected(day: Calendar)
        fun onFabClickedFrom(id: Int?)
        fun addNutritionFact(nutritionFact: NutritionFact)
    }
}