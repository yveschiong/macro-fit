package com.yveschiong.macrofit.injection

import android.content.Context
import com.yveschiong.macrofit.bus.EventBus
import com.yveschiong.macrofit.contracts.*
import com.yveschiong.macrofit.database.AppDatabase
import com.yveschiong.macrofit.database.daos.FoodDao
import com.yveschiong.macrofit.database.daos.NutritionFactsDao
import com.yveschiong.macrofit.presenters.*
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

    @Provides
    @Singleton
    fun provideMainPresenter(presenter: MainPresenter<MainViewContract.View>):
        MainViewContract.Presenter<MainViewContract.View> {
        return presenter
    }

    @Provides
    @Singleton
    fun provideAddNutritionFactPresenter(presenter: AddNutritionFactPresenter<AddNutritionFactViewContract.View>):
        AddNutritionFactViewContract.Presenter<AddNutritionFactViewContract.View> {
        return presenter
    }

    @Provides
    @Singleton
    fun provideEditNutritionFactPresenter(presenter: EditNutritionFactPresenter<EditNutritionFactViewContract.View>):
        EditNutritionFactViewContract.Presenter<EditNutritionFactViewContract.View> {
        return presenter
    }

    @Provides
    @Singleton
    fun provideAddFoodPresenter(presenter: AddFoodPresenter<AddFoodViewContract.View>):
        AddFoodViewContract.Presenter<AddFoodViewContract.View> {
        return presenter
    }

    @Provides
    @Singleton
    fun provideEditFoodPresenter(presenter: EditFoodPresenter<EditFoodViewContract.View>):
        EditFoodViewContract.Presenter<EditFoodViewContract.View> {
        return presenter
    }

    @Provides
    @Singleton
    fun provideFoodListPresenter(presenter: FoodListPresenter<FoodViewContract.View>):
        FoodViewContract.Presenter<FoodViewContract.View> {
        return presenter
    }

    @Provides
    @Singleton
    fun provideNutritionFactsListPresenter(presenter: NutritionFactsListPresenter<NutritionFactsViewContract.View>):
        NutritionFactsViewContract.Presenter<NutritionFactsViewContract.View> {
        return presenter
    }
}