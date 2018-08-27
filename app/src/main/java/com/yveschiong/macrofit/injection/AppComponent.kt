package com.yveschiong.macrofit.injection

import android.content.Context
import com.yveschiong.macrofit.activities.AddFoodActivity
import com.yveschiong.macrofit.activities.AddNutritionFactActivity
import com.yveschiong.macrofit.activities.EditNutritionFactActivity
import com.yveschiong.macrofit.activities.MainActivity
import com.yveschiong.macrofit.bus.EventBus
import com.yveschiong.macrofit.fragments.FoodFragment
import com.yveschiong.macrofit.fragments.NutritionFactsFragment
import com.yveschiong.macrofit.repositories.FoodRepository
import com.yveschiong.macrofit.repositories.NutritionFactsRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    val context: Context
    val bus: EventBus
    val foodRepository: FoodRepository
    val nutritionFactsRepository: NutritionFactsRepository

    fun inject(activity: MainActivity)
    fun inject(activity: AddNutritionFactActivity)
    fun inject(activity: EditNutritionFactActivity)
    fun inject(activity: AddFoodActivity)
    fun inject(fragment: FoodFragment)
    fun inject(fragment: NutritionFactsFragment)
}