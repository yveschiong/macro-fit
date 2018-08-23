package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.contracts.NutritionViewContract
import com.yveschiong.macrofit.repositories.NutritionFactsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NutritionFactsListPresenter<V : NutritionViewContract.View> @Inject constructor(
    private val nutritionFactsRepository: NutritionFactsRepository
) : RootPresenter<V>(), NutritionViewContract.Presenter<V> {

    override fun start(view: V) {
        super.start(view)

        // Do nothing for now
    }

    override fun fetchNutrition() {
        nutritionFactsRepository.getNutritionFacts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { view?.showNutrition(it) }
            .addToDisposables()
    }
}
