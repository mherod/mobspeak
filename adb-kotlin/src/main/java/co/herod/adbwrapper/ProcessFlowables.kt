package co.herod.adbwrapper

import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable

internal class ProcessFactory : IProcessFactory {

    override fun observableProcess(processBuilder: ProcessBuilder): Observable<String> =
            Observable.just(processBuilder)
                    .flatMap { it.start().stringsFromProcess() }
                    .map { it.trim { it <= ' ' } }
                    .flatMap { spotAdbError(it) }
                    .doOnEach(AdbBusManager.getAdbBus())

    private fun spotAdbError(it: String): Observable<String>? =
            when {
                it.contains("/system/bin/sh") -> {
                    Observable.error(AdbError("Invalid command: $it"))
                }
                it.startsWith("Android Debug Bridge version ") -> {
                    Observable.error(AdbError("Invalid command: $it"))
                }
                it.startsWith("ERROR: ") -> {
                    Observable.error(AdbError(it))
                }
                else -> {
                    Observable.just(it)
                }
            }

    private fun Process.stringsFromProcess(): Observable<String> =
            inputStream.bufferedReader().lineSequence().toObservable()
}