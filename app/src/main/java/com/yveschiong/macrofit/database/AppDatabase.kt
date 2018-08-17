package com.yveschiong.macrofit.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.yveschiong.macrofit.constants.Constants
import com.yveschiong.macrofit.database.daos.FoodDao
import com.yveschiong.macrofit.database.daos.NutritionFactsDao
import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.models.NutritionFact

@Database(entities = [Food::class, NutritionFact::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun nutritionFactsDao(): NutritionFactsDao

    companion object {
        fun build(context: Context) =
            Room.databaseBuilder(context,
                AppDatabase::class.java, Constants.DATABASE_NAME)
                .build()
    }
}