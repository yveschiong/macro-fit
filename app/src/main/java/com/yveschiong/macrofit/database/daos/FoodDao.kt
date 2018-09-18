package com.yveschiong.macrofit.database.daos

import android.arch.persistence.room.*
import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.models.TotalFoodMacroInfo
import io.reactivex.Single

@Dao
interface FoodDao {
    @Query("SELECT * FROM food")
    fun getFood(): Single<List<Food>>

    @Query("SELECT * FROM food WHERE day_timestamp BETWEEN :from AND :to")
    fun getFoodBetweenTime(from: Long, to: Long): Single<List<Food>>

    @Query("SELECT SUM(calories) AS calories, SUM(protein) AS protein, SUM(carbs) AS carbs, SUM(fat) AS fat FROM food WHERE day_timestamp BETWEEN :from AND :to")
    fun getTotalFoodMacroInfo(from: Long, to: Long): Single<TotalFoodMacroInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(food: Food)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(food: Food)

    @Query("UPDATE food SET calories = amount / :weightAmount * :calories, protein = amount / :weightAmount * :protein, carbs = amount / :weightAmount * :carbs, fat = amount / :weightAmount * :fat WHERE fact_id = :factId")
    fun update(factId: Int, weightAmount: Float, calories: Float, protein: Float, carbs: Float, fat: Float)

    @Delete
    fun delete(food: Food)

    @Query("DELETE FROM food")
    fun deleteAll()
}