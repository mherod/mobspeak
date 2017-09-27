package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun dumpUiHierarchy(
        adbDevice: AdbDevice,
        timeout: Long = 30,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): Observable<String> = Observable.just(adbDevice)
        .flatMap {
            when {
                it.preferredUiAutomatorStrategy == 0 ->
                    adbDevice.compatDumpUiHierarchy()
                it.preferredUiAutomatorStrategy == 1 ->
                    adbDevice.primaryDumpUiHierarchy()
                it.preferredUiAutomatorStrategy == 2 ->
                    adbDevice.fallbackDumpUiHierarchy()
                else ->
                    adbDevice.compatDumpUiHierarchy()
            }
        }
        .filter { it.isNotBlank() }
        .timeout(timeout, timeUnit)
        .map {
            it.substring(
                    it.indexOf('<'),
                    it.lastIndexOf('>') + 1
            )
        }