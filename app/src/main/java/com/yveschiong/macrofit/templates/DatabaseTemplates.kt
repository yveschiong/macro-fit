package com.yveschiong.macrofit.templates

import com.yveschiong.macrofit.App
import com.yveschiong.macrofit.constants.Constants
import com.yveschiong.macrofit.models.NutritionFact
import com.yveschiong.macrofit.models.Weight
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

// Class only used to create template database data for help in development. Will be deleted for release.
class DatabaseTemplates {
    companion object {
        private val compositeDisposable = CompositeDisposable()

        fun setupBasicNutritionFacts() {
            if (Constants.DB_TESTING) {
                subscribeFunc(App.graph.foodRepository.deleteAllFood()) {
                    subscribeFunc(App.graph.nutritionFactsRepository.deleteAllNutritionFacts()) {
                        subscribe(App.graph.nutritionFactsRepository.addNutritionFact(
                            NutritionFact("Brown Rice (Raw)", 100.0f, Weight.GRAMS,
                                355.55f, 6.66f, 80.0f, 2.9f)))

                        subscribe(App.graph.nutritionFactsRepository.addNutritionFact(
                            NutritionFact("Broccoli (Raw)", 100.0f, Weight.GRAMS,
                                34.0f, 2.8f, 7.0f, 0.4f)))

                        subscribe(App.graph.nutritionFactsRepository.addNutritionFact(
                            NutritionFact("Chinese Pork Boiled Dumplings", 1.0f, Weight.PIECES,
                                41.0f, 4.27f, 3.54f, 0.98f)))
                    }
                }
            }
        }

        private fun subscribe(observable: Observable<out Any>) {
            subscribeFunc(observable) {}
        }

        private fun subscribeFunc(observable: Observable<out Any>, func: () -> (Unit)) {
            compositeDisposable.add(
                observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        func()
                    }
            )
        }
    }
}