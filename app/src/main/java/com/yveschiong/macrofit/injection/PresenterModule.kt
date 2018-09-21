package com.yveschiong.macrofit.injection

import com.yveschiong.macrofit.contracts.*
import com.yveschiong.macrofit.presenters.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {
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

    @Provides
    @Singleton
    fun provideUSDASearchListPresenter(presenter: USDASearchListPresenter<USDASearchViewContract.View>):
        USDASearchViewContract.Presenter<USDASearchViewContract.View> {
        return presenter
    }
}