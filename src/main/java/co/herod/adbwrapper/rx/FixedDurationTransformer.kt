package co.herod.adbwrapper.rx

import io.reactivex.Observable
import io.reactivex.ObservableTransformer

import java.util.concurrent.TimeUnit

open class FixedDurationTransformer(
        private val timeout: Int,
        private val timeUnit: TimeUnit
) : ObservableTransformer<String, String> {

    override fun apply(upstream: Observable<String>): Observable<String> =
            upstream.retry().timeout(timeout.toLong(), timeUnit)
}
