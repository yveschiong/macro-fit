package com.yveschiong.macrofit.injection

import android.content.Context
import com.yveschiong.macrofit.bus.ScopedBus
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    val context: Context
    val bus: ScopedBus
}