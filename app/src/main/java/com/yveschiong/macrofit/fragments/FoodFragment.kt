package com.yveschiong.macrofit.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yveschiong.easycalendar.utils.CalendarUtils
import com.yveschiong.macrofit.App
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.adapters.FoodListAdapter
import com.yveschiong.macrofit.bus.events.DateEvents
import com.yveschiong.macrofit.extensions.afterMeasured
import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.models.Weight
import com.yveschiong.macrofit.presenters.FoodListPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_food.view.*
import java.util.*

class FoodFragment: Fragment() {

    private var disposable = CompositeDisposable()

    private var adapter: FoodListAdapter? = null

    companion object {
        fun newInstance(): FoodFragment {
            return FoodFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_food, container, false)

        App.graph.foodRepository.deleteAllFood()
            .subscribeOn(Schedulers.io())
            .subscribe {
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DATE, -2)

                // Add new test food after deleting old food
                App.graph.foodRepository.addFood(Food(calendar.timeInMillis, "Brown Rice 1", 175.0f, Weight.PIECES, 622.22f, 11.67f, 140.0f, 5.08f))

                calendar.add(Calendar.DATE, 1)

                App.graph.foodRepository.addFood(Food(calendar.timeInMillis, "Brown Rice 2", 175.0f, Weight.PIECES, 622.22f, 11.67f, 140.0f, 5.08f))

                calendar.add(Calendar.DATE, 1)

                App.graph.foodRepository.addFood(Food(calendar.timeInMillis, "Brown Rice 3", 175.0f, Weight.GRAMS, 622.22f, 11.67f, 140.0f, 5.08f))

                calendar.add(Calendar.DATE, 1)

                App.graph.foodRepository.addFood(Food(calendar.timeInMillis, "Brown Rice 4", 175.0f, Weight.GRAMS, 622.22f, 11.67f, 140.0f, 5.08f))

                calendar.add(Calendar.DATE, 1)

                App.graph.foodRepository.addFood(Food(calendar.timeInMillis, "Brown Rice 5", 175.0f, Weight.PIECES, 622.22f, 11.67f, 140.0f, 5.08f))
            }

        view.afterMeasured { view.recyclerView.setEmptyView(emptyView) }
        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.isNestedScrollingEnabled = false

        // Use today's time range for the initial fetch
        val range = CalendarUtils.createCalendarRange()

        App.graph.foodRepository.getFoodBetweenTime(range.start.timeInMillis, range.end.timeInMillis)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                adapter = FoodListAdapter(FoodListPresenter(it))
                view.recyclerView.adapter = adapter
            }

        return view
    }

    override fun onResume() {
        super.onResume()

        // Register to event bus for switched date events
        disposable.add(App.graph.bus.listen<DateEvents.SwitchedDateEvent>()
            .subscribeOn(Schedulers.io())
            .switchMap { day ->
                val range = CalendarUtils.createCalendarRange(day.switchedDate)

                // Get new foods that match the day criteria
                App.graph.foodRepository.getFoodBetweenTime(
                    range.start.timeInMillis, range.end.timeInMillis
                ).subscribeOn(Schedulers.io())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                adapter?.presenter = FoodListPresenter(it)
                adapter?.notifyDataSetChanged()
            })
    }

    override fun onPause() {
        super.onPause()

        // Unregister from event bus
        disposable.clear()
    }
}