package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.bus.EventBus
import com.yveschiong.macrofit.bus.events.UpdateEvents
import com.yveschiong.macrofit.contracts.NutritionFactsViewContract
import com.yveschiong.macrofit.models.NutritionFact
import com.yveschiong.macrofit.repositories.NutritionFactsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NutritionFactsListPresenter<V : NutritionFactsViewContract.View> @Inject constructor(
    private val bus: EventBus,
    private val nutritionFactsRepository: NutritionFactsRepository
) : RootPresenter<V>(), NutritionFactsViewContract.Presenter<V> {

    override fun onAttach(view: V) {
        super.onAttach(view)

        // For now, the add, edit, and delete events will just cause
        // a complete fetch of the nutrition facts table
        bus.listen<UpdateEvents.AddedNutritionFactEvent>()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { fetchNutrition() }
            .addToDisposables()

        bus.listen<UpdateEvents.EditedNutritionFactEvent>()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { fetchNutrition() }
            .addToDisposables()

        bus.listen<UpdateEvents.DeletedNutritionFactEvent>()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { fetchNutrition() }
            .addToDisposables()
    }

    override fun fetchNutrition() {
        nutritionFactsRepository.getNutritionFacts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { view?.showNutrition(it) }
            .addToDisposables()
    }

    override fun onCardClicked(nutritionFact: NutritionFact) {
        view?.showEditNutritionFactActivity(nutritionFact)
    }
}
