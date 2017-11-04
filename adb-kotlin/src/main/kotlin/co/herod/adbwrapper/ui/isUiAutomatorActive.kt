package co.herod.adbwrapper.ui

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.ui.uiautomator.pingUiAutomatorBridge
import java.util.concurrent.TimeUnit

fun AdbDevice.isUiAutomatorActive(): Boolean {
    val sec: Long = 1000
    return Blah.subject
            .map {
                if (it.not()) {
                    pingUiAutomatorBridge()
                }; else it
            }
            .timeout(sec / 5, TimeUnit.MILLISECONDS)
            .onErrorReturn { pingUiAutomatorBridge() }
            .timeout(sec / 3, TimeUnit.MILLISECONDS)
            .retry(3)
            .timeout(sec, TimeUnit.MILLISECONDS)
            .onErrorReturn { false }
            .blockingFirst(false)
}