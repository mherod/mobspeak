package co.herod.adbwrapper

import co.herod.adbwrapper.AdbBusManager.outputBus
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import java.io.BufferedReader
import java.util.concurrent.TimeUnit

fun outputAsObservable(adbCommand: AdbCommand): Observable<String> =
        AdbCommand.Builder()
                .setDeviceIdentifier(adbCommand.deviceIdentifier)
                .setCommand(SHELL)
                .buildProcess()?.run {

            outputStream.bufferedWriter().run {
                adbCommand.shellInternalCommand()
                        .plus("; exit")
                        .split(";")
                        .map { "$it; \n" }
                        .forEach { write(it) }
                        .also { flush() }
            }

            inputStream.bufferedReader().run {
                toObservable().spotAdbError()
                        .timeout(8, TimeUnit.SECONDS)
                        .doOnError { destroyForcibly() }
                        .doOnComplete { destroyForcibly() }
            }

        } ?: Observable.error(IllegalStateException())

internal fun ProcessBuilder.toObservable(): Observable<String> =
        Observable.just(this)
                .flatMap { it.start().toObservableOutput() }
                .flatMap { spotAdbError(it) }
                .doOnEach(outputBus)

internal fun Process.toObservableOutput(): Observable<String> =
        inputStream.bufferedReader().toObservable()

internal fun BufferedReader.toObservable(): Observable<String> =
        this.lineSequence().toObservable().cache()

