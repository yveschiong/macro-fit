package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.interfaces.BasePresenter
import com.yveschiong.macrofit.interfaces.BaseView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class RootPresenter<V: BaseView> : BasePresenter<V> {

    private val disposables = CompositeDisposable()

    var view: V? = null
        private set

    override fun start(view: V) {
        this.view = view
    }

    override fun end() {
        disposables.clear()
        view = null
    }

    protected fun Disposable.addToDisposables() {
        disposables.add(this)
    }
}