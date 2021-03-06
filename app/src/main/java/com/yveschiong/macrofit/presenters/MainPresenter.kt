package com.yveschiong.macrofit.presenters

import com.yveschiong.easycalendar.utils.CalendarUtils
import com.yveschiong.macrofit.bus.EventBus
import com.yveschiong.macrofit.bus.events.DateEvents
import com.yveschiong.macrofit.bus.events.QueryTextEvent
import com.yveschiong.macrofit.bus.events.UpdateEvents
import com.yveschiong.macrofit.contracts.MainViewContract
import com.yveschiong.macrofit.extensions.getNormalString
import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.models.NutritionFact
import com.yveschiong.macrofit.repositories.FoodRepository
import com.yveschiong.macrofit.repositories.NutritionFactsRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

class MainPresenter<V : MainViewContract.View> @Inject constructor(
    private val bus: EventBus,
    private val foodRepository: FoodRepository,
    private val nutritionFactsRepository: NutritionFactsRepository
) : RootPresenter<V>(), MainViewContract.Presenter<V> {

    override fun onAttach(view: V) {
        super.onAttach(view)

        bus.listen<DateEvents.SwitchedDateEvent>()
            .call {
                // Set the action bar text
                view.setTitleText(it.switchedDate.time.getNormalString())

                // Fetch for the total macro info of the food for the selected day
                fetchTotalMacroInfo(it.switchedDate)
            }

        // For now, the add, edit, and delete events will just cause
        // a fetch for the total food macro info for that day
        bus.listen<UpdateEvents.AddedFoodEvent>().handleFoodEvent()
        bus.listen<UpdateEvents.EditedFoodEvent>().handleFoodEvent()
        bus.listen<UpdateEvents.DeletedFoodEvent>().handleFoodEvent()
    }

    // Handles any similar processes
    private fun Observable<out UpdateEvents.FoodEvent>.handleFoodEvent() {
        call {
            val day = CalendarUtils.createCalendar()
            day.timeInMillis = it.food.dayTimestamp

            fetchTotalMacroInfo(day)
        }
    }

    override fun onCalendarIconSelected() {
        view?.toggleCalendarExpansion()
    }

    override fun setMenuNavigation(id: Int) {
        view?.setNavViewCheckedItem(id)
        view?.setActionBarState(id)
        view?.setViewStates(id)
        view?.switchToFragment(id)
        view?.invalidateActionBarMenu(id)
    }

    override fun changeMonthViewSelectedDay(day: Calendar) {
        view?.setMonthViewDay(day)
        view?.setTitleText(day.time.getNormalString())
    }

    override fun incrementMonthViewMonthBy(month: Calendar, increment: Int) {
        month.add(Calendar.MONTH, increment)
        view?.setMonthViewMonth(month)
    }

    override fun onMonthViewDaySelected(day: Calendar) {
        // Post a switched date event when the month view selects a different date
        view?.toggleExpandedAppBar()
        bus.post(DateEvents.SwitchedDateEvent(day))
    }

    override fun onFabClickedFrom(id: Int?) {
        view?.showActivity(id)
    }

    override fun addNutritionFact(nutritionFact: NutritionFact) {
        nutritionFactsRepository.addNutritionFact(nutritionFact)
            .subscribeOn(Schedulers.io())
            .subscribe {
                // Signal to listeners that the nutrition fact has been added
                bus.post(UpdateEvents.AddedNutritionFactEvent(nutritionFact))
            }
            .addToDisposables()
    }

    override fun editNutritionFact(nutritionFact: NutritionFact) {
        nutritionFactsRepository.updateNutritionFact(nutritionFact)
            .subscribeOn(Schedulers.io())
            .subscribe { _ ->
                // After the editing of the nutrition fact, all the dependent food
                // must also be updated with the new changes (calculations of macros)
                foodRepository.updateFoods(nutritionFact)
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        // Do nothing for now
                    }
                    .addToDisposables()

                // Signal to listeners that the nutrition fact has been edited
                bus.post(UpdateEvents.EditedNutritionFactEvent(nutritionFact))
            }
            .addToDisposables()
    }

    override fun deleteNutritionFact(nutritionFact: NutritionFact) {
        nutritionFactsRepository.deleteNutritionFact(nutritionFact)
            .subscribeOn(Schedulers.io())
            .subscribe {
                // Signal to listeners that the nutrition fact has been deleted
                bus.post(UpdateEvents.DeletedNutritionFactEvent(nutritionFact))
            }
            .addToDisposables()
    }

    override fun addFood(food: Food) {
        foodRepository.addFood(food)
            .subscribeOn(Schedulers.io())
            .subscribe {
                // Signal to listeners that a new food has been added
                bus.post(UpdateEvents.AddedFoodEvent(food))
            }
            .addToDisposables()
    }

    override fun editFood(food: Food) {
        foodRepository.updateFood(food)
            .subscribeOn(Schedulers.io())
            .subscribe {
                // Signal to listeners that the food has been edited
                bus.post(UpdateEvents.EditedFoodEvent(food))
            }
            .addToDisposables()
    }

    override fun deleteFood(food: Food) {
        foodRepository.deleteFood(food)
            .subscribeOn(Schedulers.io())
            .subscribe {
                // Signal to listeners that the food has been deleted
                bus.post(UpdateEvents.DeletedFoodEvent(food))
            }
            .addToDisposables()
    }

    override fun fetchTotalMacroInfo(day: Calendar) {
        val range = CalendarUtils.createCalendarRange(day)

        // Fetch for the total macro info of the food for the selected day
        foodRepository.getTotalFoodMacroInfo(range.start.timeInMillis, range.end.timeInMillis)
            .call {
                val total = it.protein + it.carbs + it.fat
                // Ignore 0 totals
                val conversion = if (total == 0.0f) 0.0f else 100.0f / total

                // Round everything so we can show it nicely
                view?.showTotalMacroInfo(it.calories.roundToInt(), it.protein.roundToInt(), it.carbs.roundToInt(), it.fat.roundToInt(),
                    (it.protein * conversion).roundToInt(), (it.carbs * conversion).roundToInt(), (it.fat * conversion).roundToInt())
            }
    }

    override fun search(query: String?) {
        bus.post(QueryTextEvent(query))
    }
}
