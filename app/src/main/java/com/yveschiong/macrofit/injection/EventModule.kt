package com.yveschiong.macrofit.injection

import com.yveschiong.macrofit.bus.EventBus
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.PublishSubject
import javax.inject.Singleton

@Module
class EventModule {
    @Provides
    @Singleton
    fun providePublishSubject(): PublishSubject<Any> {
        return PublishSubject.create()
    }

    @Provides
    @Singleton
    fun provideEventBus(publishSubject: PublishSubject<Any>): EventBus {
        return EventBus(publishSubject)
    }
}