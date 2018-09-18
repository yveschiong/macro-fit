package com.yveschiong.macrofit.contracts

import com.yveschiong.macrofit.interfaces.BasePresenter
import com.yveschiong.macrofit.interfaces.BaseView

interface USDASearchViewContract {
    interface View : BaseView {

    }

    interface Presenter<V: View> : BasePresenter<V> {

    }
}