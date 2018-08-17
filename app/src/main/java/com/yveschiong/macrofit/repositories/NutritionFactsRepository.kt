package com.yveschiong.macrofit.repositories

import com.yveschiong.macrofit.database.daos.NutritionFactsDao
import com.yveschiong.macrofit.models.NutritionFact
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class NutritionFactsRepository(private val nutritionFactsDao: NutritionFactsDao) {
    fun getNutritionFacts(): Observable<List<NutritionFact>> {
        return nutritionFactsDao.getNutritionFacts().toObservable()
    }

    fun addNutritionFact(nutritionFact: NutritionFact) {
        Observable.fromCallable { nutritionFactsDao.insert(nutritionFact) }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    // Want to get the observable so we can observe the deletion
    fun deleteAllNutritionFacts(): Observable<Unit> {
        return Observable.fromCallable { nutritionFactsDao.deleteAll() }
    }
}