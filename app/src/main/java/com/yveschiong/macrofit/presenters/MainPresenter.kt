package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.bus.EventBus
import com.yveschiong.macrofit.bus.events.DateEvents
import com.yveschiong.macrofit.bus.events.UpdateEvents
import com.yveschiong.macrofit.contracts.MainViewContract
import com.yveschiong.macrofit.extensions.getNormalString
import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.models.NutritionFact
import com.yveschiong.macrofit.repositories.FoodRepository
import com.yveschiong.macrofit.repositories.NutritionFactsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class MainPresenter<V : MainViewContract.View> @Inject constructor(
    private val bus: EventBus,
    private val foodRepository: FoodRepository,
    private val nutritionFactsRepository: NutritionFactsRepository
) : RootPresenter<V>(), MainViewContract.Presenter<V> {

    override fun onAttach(view: V) {
        super.onAttach(view)

        bus.listen<DateEvents.SwitchedDateEvent>()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.setTitleText(it.switchedDate.time.getNormalString())
            }
            .addToDisposables()
    }

    override fun onCalendarIconSelected() {
        view?.toggleCalendarExpansion()
    }

    override fun setMenuNavigation(id: Int) {
        view?.setNavViewCheckedItem(id)
        view?.setActionBarState(id)
        view?.switchToFragment(id)
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
            .subscribe {
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
}
