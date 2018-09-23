package com.yveschiong.macrofit
import android.app.Application
import com.yveschiong.macrofit.injection.AppComponent
import com.yveschiong.macrofit.injection.ContextModule
import com.yveschiong.macrofit.injection.DaggerAppComponent
import com.yveschiong.macrofit.templates.DatabaseTemplates

class App : Application() {

    companion object {
        // Allow access from java code
        @JvmStatic lateinit var graph: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        graph = DaggerAppComponent.builder()
                .contextModule(ContextModule(this))
                .build()

        // Used only for helping in development and not for actual release
        DatabaseTemplates.setupBasicNutritionFacts()
    }
}