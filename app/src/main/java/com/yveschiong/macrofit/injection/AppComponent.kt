package com.yveschiong.macrofit.injection

import android.content.Context
import com.yveschiong.macrofit.bus.EventBus
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    val context: Context
    val bus: EventBus
}