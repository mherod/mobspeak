package co.herod.adbwrapper.ui

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.ui.uiautomator.pingUiAutomatorBridge
import java.util.concurrent.TimeUnit

fun AdbDevice.isUiAutomatorActive(): Boolean = Blah.subject
        .map { if (it.not()) { pingUiAutomatorBridge() }; else it }
        .timeout(200, TimeUnit.MILLISECONDS)
        .onErrorReturn { pingUiAutomatorBridge() }
        .timeout(300, TimeUnit.MILLISECONDS)
        .blockingFirst(false)