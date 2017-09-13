package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import java.io.BufferedReader
import java.io.BufferedWriter
import java.util.concurrent.TimeUnit

object ProcessFactory {

    fun observableShellProcess(adbCommand: AdbCommand): Observable<String> {
        return shell(adbCommand.deviceIdentifier)?.run {
            outputStream.bufferedWriter().use { bw: BufferedWriter ->

                adbCommand
                        .shellInternalCommand()
                        .plus("; exit")
                        .split(";")
                        .map { "$it; \n" }
                        .forEach(bw::write)
                        .also { bw.flush() }
            }
            inputStream.bufferedReader().use { br: BufferedReader ->
                br.lineSequence()
                        .toObservable()
                        .spotAdbError()
                        .timeout(5, TimeUnit.SECONDS)
                        .doOnError { destroyForcibly() }
                        .doOnComplete { destroyForcibly() }
            }
        } ?: Observable.error(IllegalStateException())
    }

    private fun AdbDevice.shell(): Process? {
        return shell(this.deviceIdentifier)
    }

    private fun shell(deviceIdentifier: String?): Process? {
        return AdbCommand.Builder()
                .setDeviceIdentifier(deviceIdentifier)
                .setCommand("shell")
                .buildProcess()
    }

    fun observableProcess(processBuilder: ProcessBuilder): Observable<String> =
            Observable.just(processBuilder)
                    // .doOnNext { System.out.println(it.command()) }
                    .flatMap { it.start().stringsFromProcess() }
                    .map { it.trim() }
                    .flatMap { spotAdbError(it) }
                    .doOnEach(AdbBusManager.getAdbBus())

    private fun Observable<String>.spotAdbError(): Observable<String> =
            this.flatMap { spotAdbError(it) }

    private fun spotAdbError(it: String): Observable<String>? =
            when {
                it.startsWith("Android Debug Bridge version ") -> {
                    Observable.error(AdbError("Invalid output: $it"))
                }
                it.contains("/system/bin/sh") -> {
                    Observable.error(AdbError("Invalid output: $it"))
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