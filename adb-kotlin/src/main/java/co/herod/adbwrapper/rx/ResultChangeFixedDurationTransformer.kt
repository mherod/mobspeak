package co.herod.adbwrapper.rx

import io.reactivex.Observable

import java.util.concurrent.TimeUnit

class ResultChangeFixedDurationTransformer : FixedDurationTransformer(2, TimeUnit.DAYS) {

    override fun apply(upstream: Observable<String>): Observable<String> {
        return super.apply(upstream.repeat()).distinctUntilChanged()
    }
}
