package com.yveschiong.macrofit.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideAppContext(): Context {
        return context
    }
}