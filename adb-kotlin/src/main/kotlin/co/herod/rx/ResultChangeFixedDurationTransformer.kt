package co.herod.rx

import io.reactivex.Observable

import java.util.concurrent.TimeUnit

class ResultChangeFixedDurationTransformer<T> : FixedDurationTransformer<T>(
        timeout = 2,
        timeUnit = TimeUnit.DAYS
) {

    override fun apply(upstream: Observable<T>): Observable<T> {
        return super.apply(upstream.repeat()).distinctUntilChanged()
    }
}
