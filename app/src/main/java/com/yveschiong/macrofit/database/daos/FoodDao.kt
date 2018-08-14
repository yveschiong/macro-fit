package com.yveschiong.macrofit.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.yveschiong.macrofit.models.Food
import io.reactivex.Single

@Dao
interface FoodDao {
    @Query("SELECT * FROM food")
    fun getFood(): Single<List<Food>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(food: Food)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(food: List<Food>)

    @Query("DELETE FROM food")
    fun deleteAll()
}