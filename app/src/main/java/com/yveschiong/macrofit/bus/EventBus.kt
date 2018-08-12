package com.yveschiong.macrofit.bus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class EventBus {

    @PublishedApi
    internal val publisher: PublishSubject<Any> = PublishSubject.create()

    inline fun <reified T> listen(): Observable<T> {
        return publisher.filter {
            it is T
        }.map {
            it as T
        }
    }

    fun post(event: Any) {
        publisher.onNext(event)
    }
}
