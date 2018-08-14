package com.yveschiong.macrofit.repositories

import android.util.Log
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
            .subscribe {
                Log.d("FoodRepository", "Inserted ${food.id} into db")
            }
    }
}