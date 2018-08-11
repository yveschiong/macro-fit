package com.yveschiong.macrofit.bus.events

import java.util.*

class DateEvents: Event() {
    class SwitchedDateEvent(val switchedDate: Calendar): Event()
}