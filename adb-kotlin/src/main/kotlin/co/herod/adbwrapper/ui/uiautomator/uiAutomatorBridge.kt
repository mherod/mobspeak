package co.herod.adbwrapper.ui.uiautomator

import co.herod.adbwrapper.S
import co.herod.adbwrapper.device.*
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.ui.Blah
import co.herod.adbwrapper.ui.isUiAutomatorActive
import io.reactivex.Observable
import java.io.File

fun AdbDevice.uiAutomatorBridge(): Observable<Boolean> {

    val uiAutomatorActive = isUiAutomatorActive()

    if (uiAutomatorActive.not()) {

        println("uiAutomatorActive $uiAutomatorActive")

        matchingInstalledPackages(S.PACKAGE_UIAUTOMATOR)
                .map { println(it); it }
                .map { pm().forceStop(it); it }
                .map { pm().kill(it); it }
                .map { pm().uninstallPackage(it); it }
                .toMutableList()

        File("/Users/matthewherod/kotlin-adb-testing/uiautomator/")
                .listFiles()
                .map { it.absolutePath }
                .filter { it.endsWith(".apk") }
                .sortedBy { it.length }
                .map { println(it); it}
                .forEach { pm().installPackage(it) }

        initUiAutomatorBridge()
        isUiAutomatorActive()
    }
    return Blah.sub1
}

//        rpcDumpUiHierarchy()
//                .take(1)
//                .observeOn(Schedulers.single())
//                .timeout(100, TimeUnit.MILLISECONDS)
//                .map { UiHierarchy(this, it) }
//                .blockingSubscribe ({ myUiHierarchyBus.onNext(it) }, {})