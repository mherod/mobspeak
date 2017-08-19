package co.herod.adbwrapper

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

internal object ProcessHelper {

    fun observableProcess(processBuilder: ProcessBuilder): Observable<String> = Flowable.just(processBuilder)
            .map{ it.start() }
            .flatMap<String> { stringsFromProcess(it) }
            .map({ it.trim({ it <= ' ' }) })
            .toObservable()
            .doOnEach(AdbBusManager.ADB_BUS)

    fun blocking(processBuilder: ProcessBuilder, timeout: Int, timeUnit: TimeUnit) {

        Completable.fromObservable(observableProcess(processBuilder))
                .blockingAwait(timeout.toLong(), timeUnit)
    }

    fun blocking(processBuilder: ProcessBuilder) {
        blocking(processBuilder, 10, TimeUnit.SECONDS)
    }

    private fun stringsFromProcess(it: Process): Flowable<String>? {
        return Flowable.fromPublisher { s ->
            it.inputStream.bufferedReader().forEachLine { s.onNext(it) }; s.onComplete()
        }
    }
}