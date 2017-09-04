package co.herod.adbwrapper

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.rxkotlin.toFlowable
import java.util.concurrent.TimeUnit

internal class ProcessFactory : IProcessFactory {

    override fun observableProcess(processBuilder: ProcessBuilder?): Observable<String> = Flowable.just(processBuilder)
            .map { it.start() }
            .flatMap { stringsFromProcess(it) }
            .map { it.trim { it <= ' ' } }
            .toObservable()
            .flatMap {
                if (it.startsWith("ERROR: ")) {
                    Observable.error(AdbError(it))
                } else {
                    Observable.just(it)
                }
            }
            .doOnEach(AdbBusManager.getAdbBus())

    override fun blocking(processBuilder: ProcessBuilder, timeout: Int, timeUnit: TimeUnit) {

        Completable.fromObservable(observableProcess(processBuilder))
                .blockingAwait(timeout.toLong(), timeUnit)
    }

    override fun blocking(processBuilder: ProcessBuilder?) {
        processBuilder?.let { blocking(it, 10, TimeUnit.SECONDS) }
    }

    private fun stringsFromProcess(it: Process): Flowable<String> {
        return it.inputStream.bufferedReader().lineSequence().toFlowable()
    }
}