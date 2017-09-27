package co.herod.adbwrapper.rx

import io.reactivex.Observable
import io.reactivex.ObservableTransformer

import java.util.concurrent.TimeUnit

open class FixedDurationTransformer<T>(
        private val timeout: Int,
        private val timeUnit: TimeUnit
) : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>):
            Observable<T> = upstream
            // .retry()
            .timeout(timeout.toLong(), timeUnit)
}
