package com.yveschiong.macrofit.bus

import org.greenrobot.eventbus.EventBus
import java.util.*

class ScopedBus {

    private val eventBus = EventBus.getDefault()
    private val objects = HashMap<Any, Boolean>()

    fun register(obj: Any) {
        if (objects.containsKey(obj) && objects[obj] == true) {
            // Been added before and already registered
            return
        }

        eventBus.register(obj)

        // Sets the registered state of this object
        objects[obj] = true
    }

    fun unregister(obj: Any) {
        if (!objects.containsKey(obj) || objects[obj] == false) {
            // Not been added before or not already registered
            return
        }

        eventBus.unregister(obj)

        // Sets the registered state of this object
        objects.remove(obj)
    }

    fun post(event: Any) {
        eventBus.post(event)
    }

    fun start() {
        for (entry in objects.entries) {
            if (entry.value) {
                continue
            }

            eventBus.register(entry.key)
            entry.setValue(true)
        }
    }

    fun stop() {
        for (entry in objects.entries) {
            if (!entry.value) {
                continue
            }

            eventBus.unregister(entry.key)
            entry.setValue(false)
        }
    }
}
