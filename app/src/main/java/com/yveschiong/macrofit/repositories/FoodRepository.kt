package com.yveschiong.macrofit.repositories

import com.yveschiong.macrofit.database.daos.FoodDao
import com.yveschiong.macrofit.models.Food
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class FoodRepository(private val foodDao: FoodDao) {
    fun getFood(): Observable<List<Food>> {
        return foodDao.getFood().toObservable()
    }

    fun addFood(food: Food) {
        Observable.fromCallable { foodDao.insert(food) }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    // Want to get the observable so we can observe the deletion
    fun deleteAllFood(): Observable<Unit> {
        return Observable.fromCallable { foodDao.deleteAll() }
    }
}