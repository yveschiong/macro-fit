package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.bus.EventBus
import com.yveschiong.macrofit.bus.events.DateEvents
import com.yveschiong.macrofit.contracts.MainViewContract
import com.yveschiong.macrofit.extensions.getNormalString
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class MainPresenter<V : MainViewContract.View> @Inject constructor(
    private val bus: EventBus
) : RootPresenter<V>(), MainViewContract.Presenter<V> {

    override fun onAttach(view: V) {
        super.onAttach(view)

        bus.listen<DateEvents.SwitchedDateEvent>()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.setTitleText(it.switchedDate.time.getNormalString())
            }
            .addToDisposables()
    }

    override fun onCalendarIconSelected() {
        view?.toggleCalendarExpansion()
    }

    override fun setMenuNavigation(id: Int) {
        view?.setNavViewCheckedItem(id)
        view?.setActionBarState(id)
        view?.switchToFragment(id)
    }

    override fun changeMonthViewSelectedDay(day: Calendar) {
        view?.setMonthViewDay(day)
        view?.setTitleText(day.time.getNormalString())
    }

    override fun incrementMonthViewMonthBy(month: Calendar, increment: Int) {
        month.add(Calendar.MONTH, increment)
        view?.setMonthViewMonth(month)
    }

    override fun onMonthViewDaySelected(day: Calendar) {
        // Post a switched date event when the month view selects a different date
        view?.toggleExpandedAppBar()
        bus.post(DateEvents.SwitchedDateEvent(day))
    }

    override fun onFabClickedFrom(id: Int?) {
        view?.showActivity(id)
    }
}
