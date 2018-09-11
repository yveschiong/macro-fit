package com.yveschiong.macrofit.database.daos

import android.arch.persistence.room.*
import com.yveschiong.macrofit.models.NutritionFact
import io.reactivex.Single

@Dao
interface NutritionFactsDao {
    @Query("SELECT * FROM nutrition_facts")
    fun getNutritionFacts(): Single<List<NutritionFact>>

    @Query("SELECT CASE WHEN EXISTS (SELECT * FROM nutrition_facts WHERE upper(name) = upper(:name)) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END")
    fun alreadyExists(name: String): Single<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(nutritionFact: NutritionFact)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(nutritionFact: NutritionFact)

    @Delete
    fun delete(nutritionFact: NutritionFact)

    @Query("DELETE FROM nutrition_facts")
    fun deleteAll()
}