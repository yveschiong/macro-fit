package com.yveschiong.macrofit.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yveschiong.macrofit.App
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.adapters.FoodListAdapter
import com.yveschiong.macrofit.bus.events.DateEvents
import com.yveschiong.macrofit.extensions.afterMeasured
import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.presenters.FoodListPresenter
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_food.view.*
import java.util.*

class FoodFragment: Fragment() {

    private var disposable: Disposable? = null

    private var adapter: FoodListAdapter? = null

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

        adapter = FoodListAdapter(FoodListPresenter(testFoodList))

        view.afterMeasured { view.recyclerView.setEmptyView(emptyView) }
        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.adapter = adapter
        view.recyclerView.isNestedScrollingEnabled = false

        return view
    }

    override fun onResume() {
        super.onResume()

        // Register to event bus for switched date events
        disposable = App.graph.bus.listen<DateEvents.SwitchedDateEvent>()
                .subscribe {
                    val testFoodList = ArrayList<Food>()

                    val food = Food(it.switchedDate.timeInMillis, "Brown Rice", 175.0f, 622.22f, 11.67f, 140.0f, 5.08f)
                    testFoodList.add(food)

                    adapter?.presenter = FoodListPresenter(testFoodList)
                    adapter?.notifyDataSetChanged()
                }
    }

    override fun onPause() {
        super.onPause()

        // Unregister from event bus
        disposable?.dispose()
    }
}