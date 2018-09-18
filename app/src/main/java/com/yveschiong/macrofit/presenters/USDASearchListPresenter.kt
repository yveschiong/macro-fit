package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.contracts.USDASearchViewContract
import javax.inject.Inject

class USDASearchListPresenter<V : USDASearchViewContract.View> @Inject constructor(

) : RootPresenter<V>(), USDASearchViewContract.Presenter<V> {

    override fun onAttach(view: V) {
        super.onAttach(view)
    }
}
