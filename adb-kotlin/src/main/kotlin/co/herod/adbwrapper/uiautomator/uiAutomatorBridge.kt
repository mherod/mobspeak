package co.herod.adbwrapper.uiautomator

import co.herod.adbwrapper.S
import co.herod.adbwrapper.command
import co.herod.adbwrapper.device.*
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.ui.Blah
import co.herod.adbwrapper.ui.dump.rpcDumpUiHierarchy
import co.herod.adbwrapper.ui.isUiAutomatorActive
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.File

fun AdbDevice.uiAutomatorBridge(): Observable<Boolean> {

    println("chips and beans")

    if (isUiAutomatorActive().not()) {

        matchingInstalledPackages(S.PACKAGE_UIAUTOMATOR)
                .forEach { pm().uninstallPackage(it) }

        println("installing uiautomator stub")

        File("/Users/matthewherod/kotlin-adb-testing/uiautomator/")
                .listFiles()
                .map { it.absolutePath }
                .filter { it.endsWith(".apk") }
                .forEach {
                    println("installing $it")
                    pm().installPackage(it)
                }

        if (matchingInstalledPackages(S.PACKAGE_UIAUTOMATOR).size != 2) {
            throw AssertionError("Assertion failed")
        }

        println("Trying to start instrumentation")

        command(instrumentationCommand)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe { Blah.subject.onNext(true) }
                .doOnTerminate { Blah.subject.onNext(false) }
                .doOnDispose { Blah.subject.onNext(false) }
                .doOnError { Blah.subject.onNext(false) }
                .doOnNext { Blah.subject.onNext(true) }
                .retry()
                .repeat()
                .subscribe({}, {})

        if (pingUiAutomatorBridge().not()) forwardPort(remote = 9008)

        rpcDumpUiHierarchy().blockingFirst()
    }

    println("beans beans")

    return Blah.sub1
}