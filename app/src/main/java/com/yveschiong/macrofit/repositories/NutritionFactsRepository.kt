package com.yveschiong.macrofit.repositories

import com.yveschiong.macrofit.database.daos.NutritionFactsDao
import com.yveschiong.macrofit.models.NutritionFact
import io.reactivex.Observable

class NutritionFactsRepository(private val nutritionFactsDao: NutritionFactsDao) {
    fun getNutritionFacts(): Observable<List<NutritionFact>> {
        return nutritionFactsDao.getNutritionFacts().toObservable()
    }

    fun alreadyExists(name: String): Observable<Boolean> {
        return nutritionFactsDao.alreadyExists(name).toObservable()
    }

    fun addNutritionFact(nutritionFact: NutritionFact): Observable<Unit> {
        return Observable.fromCallable { nutritionFactsDao.insert(nutritionFact) }
    }

    fun updateNutritionFact(nutritionFact: NutritionFact): Observable<Unit> {
        return Observable.fromCallable { nutritionFactsDao.update(nutritionFact) }
    }

    fun deleteNutritionFact(nutritionFact: NutritionFact): Observable<Unit> {
        return Observable.fromCallable { nutritionFactsDao.delete(nutritionFact) }
    }

    // Want to get the observable so we can observe the deletion
    fun deleteAllNutritionFacts(): Observable<Unit> {
        return Observable.fromCallable { nutritionFactsDao.deleteAll() }
    }
}