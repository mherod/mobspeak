package co.herod.adbwrapper.ui

import co.herod.adbwrapper.AdbBusManager
import java.util.concurrent.TimeUnit

fun isUiAutomatorActive() = Blah.subject
        .timeout(100, TimeUnit.MILLISECONDS)
        .blockingFirst(AdbBusManager.uiAutomatorBridgeActive)