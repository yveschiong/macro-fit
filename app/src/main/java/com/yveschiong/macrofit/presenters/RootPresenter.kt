package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.interfaces.BasePresenter
import com.yveschiong.macrofit.interfaces.BaseView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class RootPresenter<V: BaseView> : BasePresenter<V> {

    // We want to take care of disposables in this root class
    private val disposables = CompositeDisposable()

    var view: V? = null
        private set

    override fun onAttach(view: V) {
        this.view = view
    }

    override fun onDetach() {
        disposables.clear()
        view = null
    }

    // Local extension function so children presenters can easily chain a call after subscribe
    protected fun Disposable.addToDisposables() {
        disposables.add(this)
    }
}