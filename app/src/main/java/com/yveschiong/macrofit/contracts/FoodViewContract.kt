package com.yveschiong.macrofit.contracts

import com.yveschiong.macrofit.interfaces.BasePresenter
import com.yveschiong.macrofit.interfaces.BaseView
import com.yveschiong.macrofit.models.Food

interface FoodViewContract {
    interface View : BaseView {
        fun showFood(foodList: List<Food>)
        fun showEditFoodActivity(food: Food)
    }

    interface Presenter<V: View> : BasePresenter<V> {
        fun fetchFood()
        fun onCardClicked(food: Food)
    }
}