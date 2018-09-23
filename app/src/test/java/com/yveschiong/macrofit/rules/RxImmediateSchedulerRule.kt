package com.yveschiong.macrofit.rules

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.rules.TestRule
import org.junit.runners.model.Statement
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

// Rule that allows the rxjava schedulers to be set custom schedulers
class RxImmediateSchedulerRule {
    companion object {
        private val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Scheduler.Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        fun create() = TestRule { base, _ ->
            object : Statement() {
                @Throws(Throwable::class)
                override fun evaluate() {
                    RxJavaPlugins.setInitIoSchedulerHandler { immediate }
                    RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
                    RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
                    RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
                    RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }

                    try {
                        base.evaluate()
                    } finally {
                        RxJavaPlugins.reset()
                        RxAndroidPlugins.reset()
                    }
                }
            }
        }
    }
}