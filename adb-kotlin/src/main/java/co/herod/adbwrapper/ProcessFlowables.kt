package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import java.util.concurrent.TimeUnit

internal class ProcessFactory : IProcessFactory {

    override fun observableShellProcess(adbCommand: AdbCommand): Observable<String> {
        return shell(adbCommand.deviceIdentifier)?.run {
            outputStream.bufferedWriter().run {

                adbCommand.shellInternalCommand()
                        .split(";")
                        .forEach {
                            write("$it; \n")
                        }
                write("exit; \n")
                flush()
            }
            inputStream.bufferedReader().run {

                lineSequence()
                        .toObservable()
                        // .doOnNext(System.out::println)
                        .timeout(3, TimeUnit.SECONDS)
                        .doOnError { destroyForcibly() }
                        .doOnComplete { destroyForcibly() }
            }
        }?.timeout(30, TimeUnit.SECONDS)
                ?: Observable.error(IllegalStateException())
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

    override fun observableProcess(processBuilder: ProcessBuilder): Observable<String> =
            Observable.just(processBuilder)
                    // .doOnNext { System.out.println(it.command()) }
                    .flatMap { it.start().stringsFromProcess() }
                    .map { it.trim() }
                    .flatMap { spotAdbError(it) }
                    .doOnEach(AdbBusManager.getAdbBus())

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