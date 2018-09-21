package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.interfaces.BasePresenter
import com.yveschiong.macrofit.interfaces.BaseView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

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

    // Local extension function so children presenters can easily
    // call a simple function without too much boilerplate code
    protected fun <T> Observable<T>.call(callback: (T) -> Unit) {
        subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                callback(it)
            }
            .addToDisposables()
    }

    // Local extension function so children presenters can easily chain a call after subscribe
    protected fun Disposable.addToDisposables() {
        disposables.add(this)
    }
}