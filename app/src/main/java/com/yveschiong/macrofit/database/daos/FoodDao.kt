package com.yveschiong.macrofit.database.daos

import android.arch.persistence.room.*
import com.yveschiong.macrofit.models.Food
import io.reactivex.Single

@Dao
interface FoodDao {
    @Query("SELECT * FROM food")
    fun getFood(): Single<List<Food>>

    @Query("SELECT * FROM food WHERE day_timestamp BETWEEN :from AND :to")
    fun getFoodBetweenTime(from: Long, to: Long): Single<List<Food>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(food: Food)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(food: Food)

    @Delete
    fun delete(food: Food)

    @Query("DELETE FROM food")
    fun deleteAll()
}