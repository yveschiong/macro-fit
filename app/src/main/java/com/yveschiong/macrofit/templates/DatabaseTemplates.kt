package com.yveschiong.macrofit.templates

import com.yveschiong.macrofit.App
import com.yveschiong.macrofit.constants.Constants
import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.models.NutritionFact
import com.yveschiong.macrofit.models.Weight
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

// Class only used to create template database data for help in development. Will be deleted for release.
class DatabaseTemplates {
    companion object {
        private val compositeDisposable = CompositeDisposable()

        fun setupBasicNutritionFacts() {
            if (Constants.DB_TESTING) {
                val brownRice = NutritionFact("Brown Rice (Raw)", 100.0f, Weight.GRAMS,
                    355.55f, 6.66f, 80.0f, 2.9f, 1)
                val broccoli = NutritionFact("Broccoli (Raw)", 100.0f, Weight.GRAMS,
                    34.0f, 2.8f, 7.0f, 0.4f, 2)
                val dumplings = NutritionFact("Chinese Pork Boiled Dumplings", 1.0f, Weight.PIECES,
                    41.0f, 4.27f, 3.54f, 0.98f, 3)

                // Ugly mess that is only used for fast development testing
                subscribeFunc(App.graph.foodRepository.deleteAllFood()) {
                    subscribeFunc(App.graph.nutritionFactsRepository.deleteAllNutritionFacts()) {
                        subscribeFunc(App.graph.nutritionFactsRepository.addNutritionFact(brownRice)) {
                            subscribeFunc(App.graph.nutritionFactsRepository.addNutritionFact(broccoli)) {
                                subscribeFunc(App.graph.nutritionFactsRepository.addNutritionFact(dumplings)) {
                                    subscribeFunc(App.graph.foodRepository.addFood(createFood(175.0f, brownRice))) {
                                        subscribeFunc(App.graph.foodRepository.addFood(createFood(250.0f, brownRice))) {
                                            subscribeFunc(App.graph.foodRepository.addFood(createFood(200.0f, broccoli))) {
                                                subscribeFunc(App.graph.foodRepository.addFood(createFood(12.0f, dumplings))) {

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        private fun createFood(amount: Float, nutritionFact: NutritionFact): Food {
            val scale = amount / nutritionFact.amount
            return Food(Date().time, nutritionFact.name, amount, nutritionFact.unit,
                nutritionFact.calories * scale, nutritionFact.protein * scale,
                nutritionFact.carbs * scale, nutritionFact.fat * scale, nutritionFact.id)
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