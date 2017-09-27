package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun AdbDevice.fallbackDumpUiHierarchy(
        timeout: Long = 10,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): Observable<String> =
        uiautomatorDump(this)
                .map { it.split(' ').last() }
                .filter { ".xml" in it }
                .startWith("/sdcard/window_dump.xml")
                .flatMap {
                    readDeviceFile(this, "shell cat $it")
                            .doOnNext { run { preferredUiAutomatorStrategy = 2 } }
//                            .retry()
                            .timeout(maxOf(5, timeout / 3), timeUnit)
                }
                .filter { it.isXmlOutput() }
                .timeout(maxOf(timeout / 3, 10), timeUnit)
//                .retry()
                .timeout(timeout, timeUnit)