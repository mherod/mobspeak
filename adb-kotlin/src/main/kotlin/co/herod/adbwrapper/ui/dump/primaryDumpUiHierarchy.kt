/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.ui.dump

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.uiautomatorDumpExecOut
import co.herod.adbwrapper.util.isXmlOutput
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun AdbDevice.primaryDumpUiHierarchy(
        timeout: Long = 6,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): Observable<String> {
    println("primaryDumpUiHierarchy")
    return uiautomatorDumpExecOut(this)
            .filter { it.isXmlOutput() }
            .doOnNext { preferredUiAutomatorStrategy = 1 }
            .timeout(maxOf(5, timeout / 3), timeUnit)
            .retry(1)
            .timeout(timeout, timeUnit)
}