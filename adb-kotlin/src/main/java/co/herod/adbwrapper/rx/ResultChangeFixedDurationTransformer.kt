package co.herod.adbwrapper.rx

import io.reactivex.Observable

import java.util.concurrent.TimeUnit

class ResultChangeFixedDurationTransformer<T> : FixedDurationTransformer<T>(2, TimeUnit.DAYS) {

    override fun apply(upstream: Observable<T>): Observable<T> {
        return super.apply(upstream.repeat()).distinctUntilChanged()
    }
}
