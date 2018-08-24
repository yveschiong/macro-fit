package com.yveschiong.macrofit.interfaces

interface BasePresenter<V: BaseView> {
    fun onAttach(view: V)
    fun onDetach()
}