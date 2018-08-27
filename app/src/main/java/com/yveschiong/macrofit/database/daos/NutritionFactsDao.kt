package com.yveschiong.macrofit.database.daos

import android.arch.persistence.room.*
import com.yveschiong.macrofit.models.NutritionFact
import io.reactivex.Single

@Dao
interface NutritionFactsDao {
    @Query("SELECT * FROM nutrition_facts")
    fun getNutritionFacts(): Single<List<NutritionFact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(nutritionFact: NutritionFact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(nutritionFact: List<NutritionFact>)

    @Delete
    fun delete(nutritionFact: NutritionFact)

    @Query("DELETE FROM nutrition_facts")
    fun deleteAll()
}