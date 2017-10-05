package co.herod.adbwrapper.uiautomator

import co.herod.adbwrapper.S
import co.herod.adbwrapper.command
import co.herod.adbwrapper.device.*
import co.herod.adbwrapper.device.processes.murder
import co.herod.adbwrapper.environment.forwardPort
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.ui.Blah
import co.herod.adbwrapper.ui.dump.rpcDumpUiHierarchy
import co.herod.adbwrapper.ui.isUiAutomatorActive
import co.herod.kotlin.ext.waitUntilEmpty
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File

fun AdbDevice.uiAutomatorBridge(): Observable<Boolean> {

    println("chips and beans")

    if (isUiAutomatorActive().not()) {

        matchingInstalledPackages(S.PACKAGE_UIAUTOMATOR)
                .map { println(it); it }
                .map { pm().forceStop(it); it }
                .map { pm().kill(it); it }
                .map { pm().uninstallPackage(it); it }
                .toMutableList()

//        var size: Int
//        do {
        File("/Users/matthewherod/kotlin-adb-testing/uiautomator/")
                .listFiles()
                .map { it.absolutePath }
                .filter { it.endsWith(".apk") }
                .sortedBy { it.length }
                .forEach {
                    println("installing $it")
                    pm().installPackage(it)
                }
//
//            size = matchingInstalledPackages(S.PACKAGE_UIAUTOMATOR).size
//        } while (size != 2)

        println("Trying to start instrumentation")

        val compositeDisposable = CompositeDisposable()

        while (pingUiAutomatorBridge().not()) {

            murder(S.PACKAGE_UIAUTOMATOR_TEST).subscribe()
                    .let { compositeDisposable.add(it) }

            murder(S.PACKAGE_UIAUTOMATOR).subscribe()
                    .let { compositeDisposable.add(it) }

            compositeDisposable.waitUntilEmpty()

            println("launcher instr")

            command(instrumentationCommand)
                    .observeOn(Schedulers.newThread())
                    .subscribeOn(Schedulers.newThread())
                    .doOnSubscribe { Blah.subject.onNext(true) }
                    .doOnTerminate { Blah.subject.onNext(false) }
                    .doOnDispose { Blah.subject.onNext(false) }
                    .doOnError { Blah.subject.onNext(false) }
                    .doOnNext { Blah.subject.onNext(true) }
                    .takeUntil { "INSTRUMENTATION_" in it }
                    .retry()
                    .repeat()
                    .blockingSubscribe { println(it) }


            println("forwarding port instr")

            forwardPort(remote = 9008)
        }

        println("blocking for first")

        rpcDumpUiHierarchy().blockingFirst()
    }

    println("beans beans")

    return Blah.sub1
}
