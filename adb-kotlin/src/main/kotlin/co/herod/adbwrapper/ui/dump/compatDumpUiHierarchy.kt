package co.herod.adbwrapper.ui.dump

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.uiautomatorDumpFull
import co.herod.adbwrapper.util.isXmlOutput
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun AdbDevice.compatDumpUiHierarchy(
        timeout: Long = 30,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): Observable<String> {
    println("compatDumpUiHierarchy")
    return uiautomatorDumpFull(this)
            .filter { it.isXmlOutput() }
            .doOnNext { preferredUiAutomatorStrategy = 0 }
            .timeout(maxOf(5, timeout / 3), timeUnit)
//                .retry()
            .timeout(timeout, timeUnit)
}