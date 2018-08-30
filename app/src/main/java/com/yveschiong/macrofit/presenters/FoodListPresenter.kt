package com.yveschiong.macrofit.presenters

import com.yveschiong.easycalendar.utils.CalendarUtils
import com.yveschiong.macrofit.bus.EventBus
import com.yveschiong.macrofit.bus.events.DateEvents
import com.yveschiong.macrofit.bus.events.UpdateEvents
import com.yveschiong.macrofit.contracts.FoodViewContract
import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.repositories.FoodRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FoodListPresenter<V: FoodViewContract.View> @Inject constructor(
    private val bus: EventBus,
    private val foodRepository: FoodRepository
) : RootPresenter<V>(), FoodViewContract.Presenter<V> {

    override fun onAttach(view: V) {
        super.onAttach(view)

        // Register to event bus for switched date events
        bus.listen<DateEvents.SwitchedDateEvent>()
            .subscribeOn(Schedulers.io())
            .switchMap { event ->
                val range = CalendarUtils.createCalendarRange(event.switchedDate)

                // Get new foods that match the day criteria
                foodRepository.getFoodBetweenTime(
                    range.start.timeInMillis, range.end.timeInMillis
                ).subscribeOn(Schedulers.io())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { view.showFood(it) }
            .addToDisposables()

        // For now, the add, edit, and delete events will just cause
        // a complete fetch of the food table
        bus.listen<UpdateEvents.AddedFoodEvent>()
            .subscribeOn(Schedulers.io())
            .switchMap { event ->
                val calendar = CalendarUtils.createCalendar()
                calendar.timeInMillis = event.food.dayTimestamp
                val range = CalendarUtils.createCalendarRange(calendar)

                // Get new foods that match the day criteria
                foodRepository.getFoodBetweenTime(
                    range.start.timeInMillis, range.end.timeInMillis
                ).subscribeOn(Schedulers.io())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { view.showFood(it) }
            .addToDisposables()

        bus.listen<UpdateEvents.EditedFoodEvent>()
            .subscribeOn(Schedulers.io())
            .switchMap { event ->
                val calendar = CalendarUtils.createCalendar()
                calendar.timeInMillis = event.food.dayTimestamp
                val range = CalendarUtils.createCalendarRange(calendar)

                // Get new foods that match the day criteria
                foodRepository.getFoodBetweenTime(
                    range.start.timeInMillis, range.end.timeInMillis
                ).subscribeOn(Schedulers.io())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { view.showFood(it) }
            .addToDisposables()

        bus.listen<UpdateEvents.DeletedFoodEvent>()
            .subscribeOn(Schedulers.io())
            .switchMap { event ->
                val calendar = CalendarUtils.createCalendar()
                calendar.timeInMillis = event.food.dayTimestamp
                val range = CalendarUtils.createCalendarRange(calendar)

                // Get new foods that match the day criteria
                foodRepository.getFoodBetweenTime(
                    range.start.timeInMillis, range.end.timeInMillis
                ).subscribeOn(Schedulers.io())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { view.showFood(it) }
            .addToDisposables()
    }

    override fun fetchFood() {
        // Use today's time range for the initial fetch
        val range = CalendarUtils.createCalendarRange()

        foodRepository.getFoodBetweenTime(range.start.timeInMillis, range.end.timeInMillis)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { view?.showFood(it) }
            .addToDisposables()
    }

    override fun onCardClicked(food: Food) {
        view?.showEditFoodActivity(food)
    }
}
