package com.yveschiong.macrofit.injection

import android.content.Context
import com.yveschiong.macrofit.bus.EventBus
import com.yveschiong.macrofit.database.AppDatabase
import com.yveschiong.macrofit.database.daos.FoodDao
import com.yveschiong.macrofit.database.daos.NutritionFactsDao
import com.yveschiong.macrofit.repositories.FoodRepository
import com.yveschiong.macrofit.repositories.NutritionFactsRepository
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
    fun provideDatabase(context: Context): AppDatabase {
        return AppDatabase.build(context)
    }

    @Provides
    @Singleton
    fun provideFoodDao(appDatabase: AppDatabase): FoodDao {
        return appDatabase.foodDao()
    }

    @Provides
    @Singleton
    fun provideFoodRepository(foodDao: FoodDao): FoodRepository {
        return FoodRepository(foodDao)
    }

    @Provides
    @Singleton
    fun provideNutritionFactsDao(appDatabase: AppDatabase): NutritionFactsDao {
        return appDatabase.nutritionFactsDao()
    }

    @Provides
    @Singleton
    fun provideNutritionFactsRepository(nutritionFactsDao: NutritionFactsDao): NutritionFactsRepository {
        return NutritionFactsRepository(nutritionFactsDao)
    }
}