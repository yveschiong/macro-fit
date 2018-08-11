package com.yveschiong.macrofit
import android.app.Application
import com.yveschiong.macrofit.injection.AppComponent
import com.yveschiong.macrofit.injection.AppModule
import com.yveschiong.macrofit.injection.DaggerAppComponent

class App : Application() {

    companion object {
        // Allow access from java code
        @JvmStatic lateinit var graph: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        graph = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}