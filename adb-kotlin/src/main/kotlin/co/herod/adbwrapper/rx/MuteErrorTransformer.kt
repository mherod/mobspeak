package co.herod.adbwrapper.rx

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

class MuteErrorTransformer<T> : ObservableTransformer<T, String> {

    override fun apply(upstream: Observable<T>): ObservableSource<String> = upstream
            .map { "" }
            .onErrorReturn { "" }
            .filter({ !it.trim { it <= ' ' }.isEmpty() })
}
