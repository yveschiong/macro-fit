package com.yveschiong.macrofit.activities

import android.os.Bundle
import com.yveschiong.macrofit.App
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.models.Weight
import java.util.*

class AddFoodActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -2)

        // Add new test food after deleting old food
        App.graph.foodRepository.addFood(Food(calendar.timeInMillis, "Brown Rice 1", 175.0f, Weight.PIECES, 622.22f, 11.67f, 140.0f, 5.08f))

        calendar.add(Calendar.DATE, 1)

        App.graph.foodRepository.addFood(Food(calendar.timeInMillis, "Brown Rice 2", 175.0f, Weight.PIECES, 622.22f, 11.67f, 140.0f, 5.08f))

        calendar.add(Calendar.DATE, 1)

        App.graph.foodRepository.addFood(Food(calendar.timeInMillis, "Brown Rice 3", 175.0f, Weight.GRAMS, 622.22f, 11.67f, 140.0f, 5.08f))

        calendar.add(Calendar.DATE, 1)

        App.graph.foodRepository.addFood(Food(calendar.timeInMillis, "Brown Rice 4", 175.0f, Weight.GRAMS, 622.22f, 11.67f, 140.0f, 5.08f))

        calendar.add(Calendar.DATE, 1)

        App.graph.foodRepository.addFood(Food(calendar.timeInMillis, "Brown Rice 5", 175.0f, Weight.PIECES, 622.22f, 11.67f, 140.0f, 5.08f))
    }
}