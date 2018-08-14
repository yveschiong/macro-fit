package com.yveschiong.macrofit.injection

import android.content.Context
import com.yveschiong.macrofit.bus.EventBus
import com.yveschiong.macrofit.database.FoodDatabase
import com.yveschiong.macrofit.database.daos.FoodDao
import com.yveschiong.macrofit.repositories.FoodRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideAppContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideEventBus(): EventBus {
        return EventBus()
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): FoodDatabase {
        return FoodDatabase.build(context)
    }

    @Provides
    @Singleton
    fun provideFoodDao(foodDatabase: FoodDatabase): FoodDao {
        return foodDatabase.foodDao()
    }

    @Provides
    @Singleton
    fun provideFoodRepository(foodDao: FoodDao): FoodRepository {
        return FoodRepository(foodDao)
    }
}