package com.yveschiong.macrofit.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.adapters.FoodListAdapter
import com.yveschiong.macrofit.extensions.afterMeasured
import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.presenters.FoodListPresenter
import kotlinx.android.synthetic.main.fragment_food.view.*
import java.util.*

class FoodFragment: Fragment() {

    companion object {
        fun newInstance(): FoodFragment {
            return FoodFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_food, container, false)

        val testFoodList = ArrayList<Food>()

        val food = Food(System.currentTimeMillis(), "Brown Rice", 175.0f, 622.22f, 11.67f, 140.0f, 5.08f)
        testFoodList.add(food)
        testFoodList.add(food)
        testFoodList.add(food)
        testFoodList.add(food)
        testFoodList.add(food)
        testFoodList.add(food)
        testFoodList.add(food)
        testFoodList.add(food)
        testFoodList.add(food)

        view.afterMeasured { view.recyclerView.setEmptyView(emptyView) }
        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.adapter = FoodListAdapter(FoodListPresenter(testFoodList))
        view.recyclerView.isNestedScrollingEnabled = false

        return view
    }
}