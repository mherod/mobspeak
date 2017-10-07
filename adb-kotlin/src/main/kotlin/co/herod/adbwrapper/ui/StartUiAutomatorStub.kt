@file:JvmName("StartUiAutomatorStub")

package co.herod.adbwrapper.ui

import co.herod.adbwrapper.AdbDeviceManager
import co.herod.adbwrapper.ui.uiautomator.initUiAutomatorBridge

fun main(args: Array<String>) {
    AdbDeviceManager.getDevice()?.let {
        with(it) { initUiAutomatorBridge() }
    }
}

