package com.yveschiong.macrofit.injection

import android.content.Context
import com.yveschiong.macrofit.activities.*
import com.yveschiong.macrofit.bus.EventBus
import com.yveschiong.macrofit.fragments.FoodFragment
import com.yveschiong.macrofit.fragments.NutritionFactsFragment
import com.yveschiong.macrofit.fragments.USDASearchFragment
import com.yveschiong.macrofit.network.search.SearchApi
import com.yveschiong.macrofit.repositories.FoodRepository
import com.yveschiong.macrofit.repositories.NutritionFactsRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, DatabaseModule::class, EventModule::class, NetworkModule::class, PresenterModule::class])
interface AppComponent {
    val context: Context
    val bus: EventBus
    val foodRepository: FoodRepository
    val nutritionFactsRepository: NutritionFactsRepository
    val searchApi: SearchApi

    fun inject(activity: MainActivity)
    fun inject(activity: AddNutritionFactActivity)
    fun inject(activity: EditNutritionFactActivity)
    fun inject(activity: AddFoodActivity)
    fun inject(activity: EditFoodActivity)
    fun inject(fragment: FoodFragment)
    fun inject(fragment: NutritionFactsFragment)
    fun inject(fragment: USDASearchFragment)
}