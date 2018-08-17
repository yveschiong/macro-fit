package com.yveschiong.macrofit.injection

import android.content.Context
import com.yveschiong.macrofit.bus.EventBus
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
}