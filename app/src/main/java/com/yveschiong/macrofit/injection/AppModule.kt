package com.yveschiong.macrofit.injection

import android.content.Context
import com.yveschiong.macrofit.bus.ScopedBus
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
    fun provideScopedBus(): ScopedBus {
        return ScopedBus()
    }
}