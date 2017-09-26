package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.dump
import co.herod.adbwrapper.device.dumpsys
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.DumpsysKey
import co.herod.kotlin.ext.containsIgnoreCase
import co.herod.kotlin.ext.filterKeys
import co.herod.kotlin.ext.observableValues
import co.herod.kotlin.ext.retryWithTimeout
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun AdbDevice.matchActivity(activityName: String, timeout: Int, timeUnit: TimeUnit): Boolean =
        Observable.just(this)
                .flatMap { device ->
                    device.dumpsys()
                            .dump(dumpsysKey = DumpsysKey.WINDOW)
                            .filterKeys("mCurrentFocus", "mFocusedApp")
                            .observableValues()
                            .filter { it.containsIgnoreCase(activityName) }
                }
                .firstOrError()
                .retryWithTimeout(timeout, timeUnit)
                .onErrorReturn { "" } // timeout without match
                .doOnSuccess {
                    if (it.isNotBlank()) {
                        System.out.println("Matched: $it")
                    }
                }
                .blockingGet()
                .isNotBlank()