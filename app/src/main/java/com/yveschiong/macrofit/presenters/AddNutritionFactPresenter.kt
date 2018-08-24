package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.contracts.AddNutritionFactViewContract
import javax.inject.Inject

class AddNutritionFactPresenter<V : AddNutritionFactViewContract.View> @Inject constructor(

) : RootPresenter<V>(), AddNutritionFactViewContract.Presenter<V> {

}
