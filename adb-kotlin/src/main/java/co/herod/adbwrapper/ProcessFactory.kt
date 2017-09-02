package co.herod.adbwrapper

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

internal class ProcessFactory : IProcessFactory {

    override fun observableProcess(processBuilder: ProcessBuilder?): Observable<String> = Flowable.just(processBuilder)
            .map { it.start() }
            .flatMap { stringsFromProcess(it) }
            .map { it.trim { it <= ' ' } }
            .toObservable()
            .doOnEach(AdbBusManager.getAdbBus())

    override fun blocking(processBuilder: ProcessBuilder, timeout: Int, timeUnit: TimeUnit) {

        Completable.fromObservable(observableProcess(processBuilder))
                .blockingAwait(timeout.toLong(), timeUnit)
    }

    override fun blocking(processBuilder: ProcessBuilder?) {
        processBuilder?.let { blocking(it, 10, TimeUnit.SECONDS) }
    }

    private fun stringsFromProcess(it: Process): Flowable<String>? {
        return Flowable.fromPublisher { s ->
            it.inputStream.bufferedReader().forEachLine { s.onNext(it) }; s.onComplete()
        }
    }
}