package com.yveschiong.macrofit.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.yveschiong.macrofit.constants.Constants
import com.yveschiong.macrofit.database.daos.FoodDao
import com.yveschiong.macrofit.models.Food

@Database(entities = [Food::class], version = 1, exportSchema = false)
abstract class FoodDatabase: RoomDatabase() {
    abstract fun foodDao(): FoodDao

    companion object {
        fun build(context: Context) =
            Room.databaseBuilder(context,
                FoodDatabase::class.java, Constants.DATABASE_NAME)
                .build()
    }
}