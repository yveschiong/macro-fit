package com.yveschiong.macrofit.repositories

import com.yveschiong.macrofit.database.daos.FoodDao
import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.models.NutritionFact
import io.reactivex.Observable

class FoodRepository(private val foodDao: FoodDao) {
    fun getFood(): Observable<List<Food>> {
        return foodDao.getFood().toObservable()
    }

    fun getFoodBetweenTime(from: Long, to: Long): Observable<List<Food>> {
        return foodDao.getFoodBetweenTime(from, to).toObservable()
    }

    fun addFood(food: Food): Observable<Unit> {
        return Observable.fromCallable { foodDao.insert(food) }
    }

    fun updateFood(food: Food): Observable<Unit> {
        return Observable.fromCallable { foodDao.update(food) }
    }

    fun updateFoods(nutritionFact: NutritionFact): Observable<Unit> {
        return Observable.fromCallable { foodDao.update(nutritionFact.id, nutritionFact.amount, nutritionFact.calories, nutritionFact.protein, nutritionFact.carbs, nutritionFact.fat) }
    }

    fun deleteFood(food: Food): Observable<Unit> {
        return Observable.fromCallable { foodDao.delete(food) }
    }

    // Want to get the observable so we can observe the deletion
    fun deleteAllFood(): Observable<Unit> {
        return Observable.fromCallable { foodDao.deleteAll() }
    }
}