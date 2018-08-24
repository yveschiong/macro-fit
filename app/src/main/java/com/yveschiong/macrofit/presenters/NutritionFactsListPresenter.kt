package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.contracts.NutritionFactsViewContract
import com.yveschiong.macrofit.repositories.NutritionFactsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NutritionFactsListPresenter<V : NutritionFactsViewContract.View> @Inject constructor(
    private val nutritionFactsRepository: NutritionFactsRepository
) : RootPresenter<V>(), NutritionFactsViewContract.Presenter<V> {

    override fun fetchNutrition() {
        nutritionFactsRepository.getNutritionFacts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { view?.showNutrition(it) }
            .addToDisposables()
    }
}
