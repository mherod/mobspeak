package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun AdbDevice.compatDumpUiHierarchy(
        timeout: Long = 30,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): Observable<String> =
        uiautomatorDumpFull(this)
                .filter { it.isXmlOutput() }
                .doOnNext { preferredUiAutomatorStrategy = 0 }
                .timeout(maxOf(5, timeout / 3), timeUnit)
//                .retry()
                .timeout(timeout, timeUnit)