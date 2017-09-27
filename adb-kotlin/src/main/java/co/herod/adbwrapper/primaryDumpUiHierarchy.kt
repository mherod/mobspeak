package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun AdbDevice.primaryDumpUiHierarchy(
        timeout: Long = 10,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): Observable<String> =
        uiautomatorDumpExecOut(this)
                .filter { it.isXmlOutput() }
                .doOnNext { preferredUiAutomatorStrategy = 1 }
                .timeout(maxOf(5, timeout / 3), timeUnit)
                .retry()
                .timeout(timeout, timeUnit)