package co.herod.adbwrapper

import co.herod.adbwrapper.AdbBusManager.outputBus
import co.herod.adbwrapper.exceptions.AdbError
import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import java.util.concurrent.TimeUnit

object ProcessFactory {

    fun observableShellProcess(adbCommand: AdbCommand): Observable<String> =
            shell(adbCommand.deviceIdentifier)?.run {

                outputStream.bufferedWriter().run {
                    // bw: BufferedWriter ->
                    adbCommand
                            .shellInternalCommand()
                            .plus("; exit")
                            .split(";")
                            .map { "$it; \n" }
                            .forEach { write(it) }
                            .also { flush() }
                }

                inputStream.bufferedReader().run {
                    // br: BufferedReader ->
                    lineSequence()
                            .toObservable()
                            .cache()
                            .spotAdbError()
                            .timeout(5, TimeUnit.SECONDS)
                            .doOnError { destroyForcibly() }
                            .doOnComplete { destroyForcibly() }
                }

            } ?: Observable.error(IllegalStateException())

    private fun shell(deviceIdentifier: String?): Process? =
            AdbCommand.Builder()
                    .setDeviceIdentifier(deviceIdentifier)
                    .setCommand(SHELL)
                    .buildProcess()

    fun observableProcess(processBuilder: ProcessBuilder): Observable<String> =
            Observable.just(processBuilder)
                    .flatMap { it.start().stringsFromProcess() }
                    .map { it.trim() }
                    .flatMap { spotAdbError(it) }
                    .doOnEach(outputBus)

    private fun Observable<String>.spotAdbError(): Observable<String> =
            this.flatMap { spotAdbError(it) }

    private fun spotAdbError(it: String): Observable<String>? =
            when {
                it.startsWith("Android Debug Bridge version ") -> {
                    Observable.error(AdbError("Invalid output: $it"))
                }
                it.startsWith("ERROR: ") -> {
                    Observable.error(AdbError(it))
                }
                "/system/bin/sh" in it -> {
                    Observable.error(AdbError("Invalid output: $it"))
                }
                "** No activities found to run, monkey aborted" in it -> {
                    Observable.error(AdbError(it.trim()))
                }
                else -> Observable.just(it)
            }

    private fun Process.stringsFromProcess(): Observable<String> =
            inputStream
                    .bufferedReader()
                    .lineSequence()
                    .toObservable()
                    .cache()
}