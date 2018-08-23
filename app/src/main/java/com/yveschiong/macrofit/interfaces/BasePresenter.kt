package com.yveschiong.macrofit.interfaces

interface BasePresenter<V: BaseView> {
    fun start(view: V)
    fun end()
}