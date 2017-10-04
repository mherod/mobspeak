package co.herod.adbwrapper.ui

import co.herod.adbwrapper.AdbBusManager
import java.util.concurrent.TimeUnit

fun isUiAutomatorActive(): Boolean = Blah.subject
        .timeout(250, TimeUnit.MILLISECONDS)
        .blockingFirst(AdbBusManager.uiAutomatorBridgeActive)